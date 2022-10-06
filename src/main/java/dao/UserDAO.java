package dao;

import db.C3p0DataSource;
import db.DBException;
import db.Query;
import models.Role;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static db.Query.*;

public class UserDAO implements DAO<User>{
    //private final DBManager dbManager = DBManager.getInstance();
    private List<User> users = new ArrayList<>();
    public UserDAO(){
    }

    public User get(String email){
        Connection connection = C3p0DataSource.getConnection();
        User user = null;

        try {
            PreparedStatement statement = connection.prepareStatement(USER_GET_BY_EMAIL);
            statement.setString(1, String.valueOf(email));

            ResultSet rs = statement.executeQuery();
            rs.next();
            user = new User(rs.getInt("id"), email,rs.getString("password"),
                    rs.getString("name"),rs.getString("surname"),
                    Role.values()[rs.getInt("role_id")],rs.getBoolean("is_blocked"));

        } catch (SQLException e) {
            System.err.println("Caught Exception: "+ e);
        }
        return user;
    }
    @Override
    public User get(int id)  {
        Connection connection = C3p0DataSource.getConnection();
        User user = null;

        try {
            PreparedStatement statement = connection.prepareStatement("select * from Users where id = ?");
            statement.setString(1, String.valueOf(id));

            ResultSet rs = statement.executeQuery();
            rs.next();

            user = new User(id, rs.getString("email"),rs.getString("password"),
                    rs.getString("name"),rs.getString("surname"), Role.USER,false);

        } catch (SQLException e) {
            System.err.println("Caught Exception: "+ e);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void save(User user) throws DBException {
        Connection con = C3p0DataSource.getConnection();
        PreparedStatement preparedStatement = null;
        try {
           // con = dbManager.getConnection();
            preparedStatement = con.prepareStatement(Query.USER_CREATE, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            preparedStatement.setString(k++, user.getEmail());
            preparedStatement.setString(k++, user.getPassword());
            preparedStatement.setString(k++, user.getName());
            preparedStatement.setString(k++, user.getSurname());
            preparedStatement.setInt(k++, user.getRole().getId());
            preparedStatement.setBoolean(k, false);

            int count = preparedStatement.executeUpdate();
            System.out.println(count);
            setGeneratedUserId( user, preparedStatement );
            con.commit();
            preparedStatement.close();
        } catch (SQLException e) {
           // LOGGER.warn(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw  new DBException("Failed to rollback", ex);
            }
            throw  new DBException("Failed to add user", e);
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
    public void update(User user, String[] params) {

    }

    @Override
    public void delete(User user) {

    }
}
