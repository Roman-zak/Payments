package dao;

import db.C3p0DataSource;
import db.DBException;
import db.Query;
import models.Account;
import models.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements DAO<Account>{
    private final Logger logger = Logger.getLogger(AccountDAO.class);
    @Override
    public Account get(int id) throws DBException {
        Connection con = C3p0DataSource.getConnection();
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

    @Override
    public List<Account> getAll() throws DBException {
        Connection con = C3p0DataSource.getConnection();
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
            logger.warn(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new DBException("Can not get all accounts", e);
            }
            throw new DBException("Can not get all accounts", e);
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
                con.close();
            } catch (SQLException e) {
                throw new DBException("Can not get all accounts", e);
            }
        }
    }

    @Override
    public void save(Account o) throws DBException {
        Connection con = C3p0DataSource.getConnection();
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
            System.out.println("in account dao "+acc.getOwnerAddress());
            preparedStatement.setString(k++, acc.getOwnerAddress());
            preparedStatement.setString(k, acc.getPostalCode());

            int count = preparedStatement.executeUpdate();
            System.out.println(count);
            setGeneratedAccountId( acc, preparedStatement );
            con.commit();
            preparedStatement.close();


        } catch (SQLException e) {
             logger.warn(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw  new DBException("Failed to rollback", ex);
            }
            throw  new DBException("Failed to add user", e);
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
    public void update(Account o, String[] params) {

    }

    @Override
    public void delete(Account o) {

    }

    public List<Account> getAllOfUser(User user) throws DBException {
        List<Account> accounts = new ArrayList<>();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = C3p0DataSource.getConnection();
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
            logger.warn(e.getMessage());
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
            con = C3p0DataSource.getConnection();
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
                throw new DBException("Can not perform money withdraw", e);
            }
            throw new DBException("Can not perform money withdraw", e);
        } finally {
            try {
                preparedStatement.close();
                con.close();
            } catch (SQLException e) {
                throw new DBException("Can not perform money withdraw", e);
            }
        }
    }

    public Integer getIdByAccountNo(String accountNo) throws DBException {
        Connection con = C3p0DataSource.getConnection();
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
        Connection con = C3p0DataSource.getConnection();
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
            con = C3p0DataSource.getConnection();
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
}
