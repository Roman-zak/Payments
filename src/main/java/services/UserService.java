package services;

import dao.UserDAO;
import db.DBException;
import models.User;

import java.util.List;

public class UserService {
    private static UserService instance;
    private final UserDAO userDAO = new UserDAO();


    public static synchronized UserService getInstance() {

        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }
    public boolean isRegistered(String email, String password) throws DBException {
        User user = userDAO.get(email);
        if(user == null){
            return false;
        }
        return user.getEmail().equals(email) && user.getPassword().equals(password);
    }

    public User get(String email) throws DBException {
        return userDAO.get(email);
    }

    public List<User> getAll() throws DBException {
        return userDAO.getAll();
    }
}
