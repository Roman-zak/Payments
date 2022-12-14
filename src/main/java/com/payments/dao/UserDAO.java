package com.payments.dao;

import com.payments.data.DBCPDataSource;
import com.payments.data.DBException;
import com.payments.data.Query;
import com.payments.models.Role;
import com.payments.models.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.payments.data.Query.*;
public class UserDAO implements DAO<User>{
    private final Logger logger = Logger.getLogger(UserDAO.class);

    private List<User> users = new ArrayList<>();
    private int noOfRecords;

    public UserDAO(){
    }

    public User get(String email) throws DBException{
        Connection connection = DBCPDataSource.getConnection();
        User user = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
             statement = connection.prepareStatement(USER_GET_BY_EMAIL);
            statement.setString(1, email);

            rs = statement.executeQuery();
            rs.next();
            Role role;
            user = new User(rs.getInt("id"), email,rs.getString("password"),
                    rs.getString("name"),rs.getString("surname"),
                    idToRole(rs.getInt("role_id")),rs.getBoolean("is_blocked"));
        } catch (SQLException e) {
           throw new DBException("Failed to get user", e);
        } finally {
            try {
                rs.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                throw new DBException("Can not get account id by acc number", e);
            }
        }
        return user;
    }
    private Role idToRole(int id){
        return id==1? Role.USER:Role.ADMIN;
    }
    @Override
    public User get(int id)  throws DBException{
        Connection connection = DBCPDataSource.getConnection();
        User user = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(USER_FIND_BY_ID);
            statement.setString(1, String.valueOf(id));
            rs = statement.executeQuery();
            rs.next();
            user = new User(id, rs.getString("email"),rs.getString("password"),
                    rs.getString("name"),rs.getString("surname"), idToRole(rs.getInt("role_id")), rs.getBoolean("is_blocked"));

        } catch (SQLException e) {
            throw new DBException("Failed to get user", e);
        }finally {
            try {
                rs.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                throw new DBException("Can not get account id by acc number", e);
            }
        }
        return user;
    }

    @Override
    public List<User> getAll() throws DBException {
        Connection connection = DBCPDataSource.getConnection();
        List<User> users = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement  = connection.prepareStatement(USER_GET_ALL);
            rs = statement.executeQuery();
            while (rs.next()){
                User user = new User(rs.getInt("id"), rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("name"),rs.getString("surname"),
                        idToRole(rs.getInt("role_id")),rs.getBoolean("is_blocked"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DBException("Failed to get users", e);
        }finally {
            try {
                rs.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                throw new DBException("Can not get account id by acc number", e);
            }
        }
        return users;
    }

    @Override
    public void save(User user) throws DBException {
        Connection con = DBCPDataSource.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(Query.USER_CREATE, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            preparedStatement.setString(k++, user.getEmail());
            preparedStatement.setString(k++, user.getPassword());
            preparedStatement.setString(k++, user.getName());
            preparedStatement.setString(k++, user.getSurname());
            preparedStatement.setInt(k++, user.getRole().getId());
            preparedStatement.setBoolean(k, false);

            int count = preparedStatement.executeUpdate();
             
            setGeneratedUserId( user, preparedStatement );
            con.commit();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw  new DBException("Failed to rollback", ex);
            }
            throw  new DBException("Failed to add user", e);
        }finally {
            try {
                preparedStatement.close();
                con.close();
            } catch (SQLException e) {
                throw new DBException("Can not get account id by acc number", e);
            }
        }
    }
    private void setGeneratedUserId(User user, PreparedStatement stmt) throws DBException {
        ResultSet generatedKeys = null;
        try {
            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
            else {
                throw new DBException("Creating user failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new DBException("Creating user failed, no ID obtained.", e);
        }

    }
    @Override
    public void update(User user) throws DBException {
        Connection con = DBCPDataSource.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(Query.USER_UPDATE);
            int k = 1;
            preparedStatement.setString(k++, user.getEmail());
            preparedStatement.setString(k++, user.getPassword());
            preparedStatement.setString(k++, user.getName());
            preparedStatement.setString(k++, user.getSurname());
            preparedStatement.setInt(k++, user.getRole().getId());
            preparedStatement.setBoolean(k, user.isBlocked());
            preparedStatement.setInt(k++, user.getId());
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
            throw new DBException("Can not update user");
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
    public void delete(User user) throws DBException {
        Connection con = DBCPDataSource.getConnection();
        PreparedStatement preparedStatement = null;
        try {

            if(user.getId()==0){
                preparedStatement = con.prepareStatement(Query.USER_DELETE_BY_EMAIL);
                preparedStatement.setString(1, user.getEmail());
            } else{
                preparedStatement = con.prepareStatement(Query.USER_DELETE);
                preparedStatement.setInt(1, user.getId());
            }

            int count = preparedStatement.executeUpdate();
            logger.info("delete returned "+count);
            con.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
                throw new DBException("Can not rollback");
            }
            throw new DBException("Can not delete user");
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
    public Map.Entry<List<User>, Integer> getAllWithLimit(int offset, int noOfRecords) throws DBException {
        String query = "select SQL_CALC_FOUND_ROWS * from user limit " + offset + ", " + noOfRecords;
        Connection connection = DBCPDataSource.getConnection();
        List<User> users = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
             rs = stmt.executeQuery(query);
            while (rs.next()){
                User user = new User(rs.getInt("id"), rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("name"),rs.getString("surname"),
                        idToRole(rs.getInt("role_id")),rs.getBoolean("is_blocked"));
                users.add(user);
            }

            rs = stmt.executeQuery("SELECT FOUND_ROWS()");

            if (rs.next())
                this.noOfRecords = rs.getInt(1);
            connection.commit();
        } catch (SQLException ex) {
            throw new DBException("Failed to get users", ex);
        }finally {
            try {
                rs.close();
                stmt.close();
                connection.close();
            } catch (SQLException e) {
                throw new DBException("Can not get account id by acc number", e);
            }
        }
        Map<List<User>, Integer> res = new HashMap<>();
        res.put(users,this.noOfRecords );
        for ( Map.Entry<List<User>, Integer> entry: res.entrySet()){
            return entry;
        }
        return null;
    }

    public void updateStatus(User user) throws DBException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = DBCPDataSource.getConnection();
            preparedStatement = con.prepareStatement(Query.UPDATE_USER_BLOCKED_BY_ACCOUNT_ID);
            preparedStatement.setBoolean(1, !user.isBlocked());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                throw new DBException("Can not change block status", e);
            }
            throw new DBException("Can not change block status", e);
        } finally {
            try {
                preparedStatement.close();
                con.close();
            } catch (SQLException e) {
                logger.warn(e.getMessage());
                throw new DBException("Can not change block status", e);
            }
        }
    }
}
