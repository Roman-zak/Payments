package dao;

import com.payments.payments.controllers.DBManager;
import models.Role;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAO<User>{
    private final DBManager dbManager = DBManager.getInstance();
    private List<User> users = new ArrayList<>();
    public UserDAO(){
    }

    @Override
    public User get(int id) throws SQLException {
        Connection connection = dbManager.getConnection();
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
    public void save(User user) throws SQLException {
        Connection con = dbManager.getConnection();
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
            con.rollback();
            System.err.println(e);
        }
//        finally {
//            preparedStatement.close();
//        }
    }
    private void setGeneratedUserId(User user, PreparedStatement stmt) throws SQLException {
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            user.setId(generatedKeys.getInt(1));
        }
        else {
            throw new SQLException("Creating user failed, no ID obtained.");
        }
    }
    @Override
    public void update(User user, String[] params) {

    }

    @Override
    public void delete(User user) {

    }
}
