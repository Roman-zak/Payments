package com.payments.dao;

import com.payments.data.DBCPDataSource;
import com.payments.data.DBException;
import com.payments.data.Query;
import com.payments.models.Account;
import com.payments.models.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.payments.data.Query.USER_WITH_ACCOUNT_COUNT;

public class UserAccountDAO{
    private final Logger logger = Logger.getLogger(UserAccountDAO.class);
    public void save(User user, Account account) throws DBException {
        Connection con = DBCPDataSource.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(Query.USER_HAS_ACCOUNT_INSERT);
            int k = 1;
            preparedStatement.setInt(k++, user.getId());
            preparedStatement.setInt(k, account.getId());
            int count = preparedStatement.executeUpdate();
            System.out.println(count);
            con.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw  new DBException("Failed to rollback", ex);
            }
            throw  new DBException("Failed to add account", e);
        }
        finally {
            try {
                con.close();
                preparedStatement.close();
            } catch (SQLException e) {
                logger.error(e);
                throw new DBException("Can not close resources",e);
            }

        }
    }

    public void save(int user_id, int account_id) throws DBException {
        Connection con = DBCPDataSource.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(Query.USER_HAS_ACCOUNT_INSERT);
            int k = 1;
            preparedStatement.setInt(k++, user_id);
            preparedStatement.setInt(k, account_id);
            int count = preparedStatement.executeUpdate();
            System.out.println(count);
            con.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw  new DBException("Failed to rollback", ex);
            }
            throw  new DBException("Failed to add account", e);
        }
    }

    public void save(User user, Account account, Connection con) throws DBException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(Query.USER_HAS_ACCOUNT_INSERT);
            int k = 1;
            preparedStatement.setInt(k++, user.getId());
            preparedStatement.setInt(k, account.getId());
            int count = preparedStatement.executeUpdate();
            System.out.println(count);


        } catch (SQLException e) {
            logger.warn(e.getMessage());
            throw  new DBException("Failed to add account", e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                throw  new DBException("Failed to close prepare statement", ex);
            }
        }
    }

    public int getCountOfUsersWithAccount(int accountId) throws DBException {
        Connection connection = DBCPDataSource.getConnection();
        int count;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(USER_WITH_ACCOUNT_COUNT);
            statement.setInt(1, accountId);
            rs = statement.executeQuery();
            rs.next();
            count = rs.getInt("users_count");
        } catch (SQLException e) {
            throw new DBException("Failed to get users count", e);
        }finally {
            try {
                assert rs != null;
                rs.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                throw new DBException("Can not close resources get users count", e);
            }
        }
        return count;
    }
    public void delete(int accountId, int userId) throws DBException {
        Connection con = DBCPDataSource.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(Query.USER_HAS_ACCOUNT_DELETE);
            int k = 1;
            preparedStatement.setInt(k++, userId);
            preparedStatement.setInt(k, accountId);
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
            throw new DBException("Can not delete user has account record");
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

    public void delete(int accountId, int userId, Connection con) throws DBException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(Query.USER_HAS_ACCOUNT_DELETE);
            int k = 1;
            preparedStatement.setInt(k++, userId);
            preparedStatement.setInt(k, accountId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DBException("Can not delete user has account record");
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new DBException("Can not close resources");
            }
        }
    }
}
