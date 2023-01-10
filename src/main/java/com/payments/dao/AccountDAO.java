package com.payments.dao;

import com.payments.data.DBCPDataSource;
import com.payments.data.DBException;
import com.payments.data.Query;
import com.payments.models.Account;
import com.payments.models.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class AccountDAO implements DAO<Account>{
    private final Logger logger = Logger.getLogger(AccountDAO.class);
    private int noOfRecords;
    @Override
    public Account get(int id) throws DBException {
        Connection con = DBCPDataSource.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Account account = new Account();
        try {
            preparedStatement = con.prepareStatement(Query.ACCOUNT_FIND_BY_ID);
            preparedStatement.setInt(1, id);
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                if (resultSet.next()) {
                    account = getAccountFromResultSet(resultSet);
                }
            }
            con.commit();
            return account;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new DBException("Can not rollback get account by acc number", e);
            }
            throw new DBException("Can not get account by acc number", e);
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
                con.close();
            } catch (SQLException e) {
                throw new DBException("Can not close resources get account by acc number", e);
            }
        }
    }

    @Override
    public List<Account> getAll() throws DBException {
        Connection con = DBCPDataSource.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Account> accounts = new ArrayList<>();
        try {
            preparedStatement = con.prepareStatement(Query.ACCOUNT_GET_ALL);
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    Account account = getAccountFromResultSet(resultSet);
                    accounts.add(account);
                }
            }
            con.commit();
            return accounts;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new DBException("Can not rollback get all accounts", e);
            }
            throw new DBException("Can not get all accounts", e);
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
                con.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new DBException("Can not close resources get all accounts", e);
            }
        }
    }

    @Override
    public void save(Account o) throws DBException {
        Connection con = DBCPDataSource.getConnection();
        PreparedStatement preparedStatement = null;
        Account acc = (Account)o;
        try {
            preparedStatement = con.prepareStatement(Query.ACCOUNT_INSERT, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            preparedStatement.setString(k++, acc.getAccountNo());
            preparedStatement.setString(k++, acc.getCurrency());
            preparedStatement.setDouble(k++, acc.getBalance());
            preparedStatement.setString(k++, acc.getOwnerName());
            preparedStatement.setString(k++, acc.getOwnerPhone());
            preparedStatement.setString(k++, acc.getOwnerAddress());
            preparedStatement.setString(k, acc.getPostalCode());
            setGeneratedAccountId( acc, preparedStatement );
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new DBException("Can not rollback save account");
            }
            logger.warn(e.getMessage());
            throw new DBException("Can not save account");
        } finally {
            try {
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                logger.warn(ex.getMessage());
                throw new DBException("Can not close prep stmt");
            }
        }
    }
    private void setGeneratedAccountId(Account acc, PreparedStatement stmt) throws DBException {
        ResultSet generatedKeys = null;
        try {
            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                acc.setId(generatedKeys.getInt(1));
            }
            else {
                throw new DBException("Creating account failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new DBException("Creating account failed, no ID obtained.", e);
        }

    }
    @Override
    public void update(Account o) throws DBException {
        Connection con = DBCPDataSource.getConnection();
        PreparedStatement preparedStatement = null;
        Account acc = (Account)o;
        try {
            preparedStatement = con.prepareStatement(Query.ACCOUNT_UPDATE);
            int k = 1;
            preparedStatement.setString(k++, acc.getAccountNo());
            preparedStatement.setString(k++, acc.getCurrency());
            preparedStatement.setDouble(k++, acc.getBalance());
            preparedStatement.setString(k++, acc.getOwnerName());
            preparedStatement.setString(k++, acc.getOwnerPhone());
            preparedStatement.setString(k++, acc.getOwnerAddress());
            preparedStatement.setString(k++, acc.getPostalCode());
            preparedStatement.setBoolean(k, acc.isBlocked());
            preparedStatement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
                throw new DBException("Can not rollback");
            }
            throw new DBException("Can not update account");
        } finally {
            try {
                con.close();
                preparedStatement.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new DBException("Can not close resources");
            }
        }
    }

    @Override
    public void delete(Account acc) throws DBException {
        Connection con = DBCPDataSource.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(Query.ACCOUNT_DELETE);
            int k = 1;
            preparedStatement.setInt(k, acc.getId());
            preparedStatement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
                throw new DBException("Can not rollback");
            }
            throw new DBException("Can not delete account");
        } finally {
            try {
                con.close();
                preparedStatement.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new DBException("Can not close resources");
            }
        }

    }

    public List<Account> getAllOfUser(User user) throws DBException {
        List<Account> accounts = new ArrayList<>();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = DBCPDataSource.getConnection();
            preparedStatement = con.prepareStatement(Query.ACCOUNT_GET_ALL_BY_USER_ID);
            preparedStatement.setInt(1,user.getId());
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    accounts.add(getAccountFromResultSet(resultSet));
                }
            }
            con.commit();
            return accounts;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new DBException("Can't get Users from database", e);
            }
            throw new DBException("Can't get Users from database", e);
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
                con.close();
            } catch (SQLException e){
                logger.error(e.getMessage());
                throw new DBException("Can't get Users from database", e);
            }

        }
    }
    private Account getAccountFromResultSet(ResultSet resultSet) throws SQLException {
        int k = 1;
        Account account = new Account();
        account.setId(resultSet.getInt(k++));
        account.setAccountNo(resultSet.getString(k++));
        account.setCurrency(resultSet.getString(k++));
        account.setBalance(resultSet.getDouble(k++));
        account.setOwnerName(resultSet.getString(k++));
        account.setOwnerPhone(resultSet.getString(k++));
        account.setOwnerAddress(resultSet.getString(k++));
        account.setPostalCode(resultSet.getString(k++));
        account.setBlocked(resultSet.getBoolean(k));
        return account;
    }

    public void updateBalance(Account payerAccount, double newBalance) throws DBException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            con = DBCPDataSource.getConnection();
            preparedStatement = con.prepareStatement(Query.UPDATE_ACCOUNT_BALANCE_BY_ACCOUNT_ID);
            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setInt(2, payerAccount.getId());
            preparedStatement.executeUpdate();
            con.commit();

        } catch (SQLException e) {
            logger.warn(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
                throw new DBException("Can not perform money withdraw", e);
            }
            throw new DBException("Can not perform money withdraw", e);
        } finally {
            try {
                preparedStatement.close();
                con.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new DBException("Can not perform money withdraw", e);
            }
        }
    }

    public Integer getIdByAccountNo(String accountNo) throws DBException {
        Connection con = DBCPDataSource.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Integer id=0;
        try {
            preparedStatement = con.prepareStatement(Query.ACCOUNT_FIND_ID_BY_NUMBER);
            preparedStatement.setString(1, accountNo);
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                if (resultSet.next()) {
                    id = resultSet.getInt("id");
                } else{
                    return null;
                }
            }
            con.commit();
            return id;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new DBException("Can not get account id by acc number", e);
            }
            throw new DBException("Can not get account id by acc number", e);
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
                con.close();
            } catch (SQLException e) {
                throw new DBException("Can not get account id by acc number", e);
            }
        }
    }

    public Account getByAccountNo(String recipientAccountNo) throws DBException {
        Connection con = DBCPDataSource.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Account account = new Account();
        try {
            preparedStatement = con.prepareStatement(Query.ACCOUNT_FIND_BY_ACCOUNT_NO);
            preparedStatement.setString(1, recipientAccountNo);
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                if (resultSet.next()) {
                    account = getAccountFromResultSet(resultSet);
                } else {
                    return null;
                }
            }
            con.commit();
            return account;

        } catch (SQLException e) {
            logger.warn(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new DBException("Can not get account by acc number", e);
            }
            throw new DBException("Can not get account by acc number", e);
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
                con.close();
            } catch (SQLException e) {
                throw new DBException("Can not get account by acc number", e);
            }
        }
    }

    public void updateStatus(Account account) throws DBException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = DBCPDataSource.getConnection();
            if(account.isBlocked()){
                preparedStatement = con.prepareStatement(Query.REQUEST_DELETE);
                preparedStatement.setInt(1, account.getId());
                preparedStatement.executeUpdate();
            }
            preparedStatement = con.prepareStatement(Query.UPDATE_ACCOUNT_BLOCKED_BY_ACCOUNT_ID);
            preparedStatement.setBoolean(1, !account.isBlocked());
            preparedStatement.setInt(2, account.getId());
            preparedStatement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new DBException("Can not change block status", e);
            }
            throw new DBException("Can not change block status", e);
        } finally {
            try {
                preparedStatement.close();
                con.close();
            } catch (SQLException e) {
                throw new DBException("Can not change block status", e);
            }
        }
    }

    public Map.Entry<List<Account>, Integer> getAllWithLimit(int offset, int noOfRecords) throws DBException {
        String query = "select SQL_CALC_FOUND_ROWS * from account limit " + offset + ", " + noOfRecords;
        Connection connection = DBCPDataSource.getConnection();
        List<Account> accounts = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()){
                Account account = getAccountFromResultSet(rs);
                accounts.add(account);
            }
            rs = stmt.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next())
                this.noOfRecords = rs.getInt(1);
            connection.commit();
        } catch (SQLException ex) {
            logger.error(ex);
            throw new DBException("Failed to get accounts", ex);
        }finally {
            try {
                rs.close();
                stmt.close();
                connection.close();
            } catch (SQLException e) {
                logger.error(e);
                throw new DBException("Can not get account id by acc number", e);
            }
        }
        Map<List<Account>, Integer> res = new HashMap<>();
        res.put(accounts,this.noOfRecords );
        for ( Map.Entry<List<Account>, Integer> entry: res.entrySet()){
            return entry;
        }
        return null;
    }

    public void save(Account o, Connection con) throws DBException{
        PreparedStatement preparedStatement = null;
        Account acc = (Account)o;
        try {
            preparedStatement = con.prepareStatement(Query.ACCOUNT_INSERT, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            preparedStatement.setString(k++, acc.getAccountNo());
            preparedStatement.setString(k++, acc.getCurrency());
            preparedStatement.setDouble(k++, acc.getBalance());
            preparedStatement.setString(k++, acc.getOwnerName());
            preparedStatement.setString(k++, acc.getOwnerPhone());
             logger.debug("in account dao "+acc.getOwnerAddress());
            preparedStatement.setString(k++, acc.getOwnerAddress());
            preparedStatement.setString(k, acc.getPostalCode());
            preparedStatement.executeUpdate();
            setGeneratedAccountId( acc, preparedStatement );

        } catch (SQLException e) {
            logger.warn(e.getMessage());
            throw new DBException("Can not save account.");
        }finally {
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                throw new DBException("Can not close prepare statement.");
            }
        }
    }

    public void delete(int accountId, Connection con) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(Query.ACCOUNT_DELETE);
            int k = 1;
            preparedStatement.setInt(k, accountId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new SQLException("can not delete account", e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new SQLException("Can not close resources",e);
            }
        }
    }
}
