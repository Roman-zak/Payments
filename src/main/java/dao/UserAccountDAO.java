package dao;

import db.DBCPDataSource;
import db.DBException;
import db.Query;
import models.Account;
import models.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
