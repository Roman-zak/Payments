package com.payments.services;

import com.payments.dao.UserDAO;
import com.payments.data.DBException;
import com.payments.models.User;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class);
    private static UserService instance;
    private final UserDAO userDAO = new UserDAO();
    private int noOfRecords;

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

    public Map.Entry<List<User>, Integer> getAllWithLimit(int offset, int noOfRecords) throws DBException {
        return userDAO.getAllWithLimit(offset, noOfRecords);

    }

    public boolean changeUserStatus(int id) {
        try {
            User user = userDAO.get(id);
            userDAO.updateStatus(user);
        } catch (DBException e) {
            logger.warn("Can not change status", e);
            return false;
        }
        return true;
    }
}
