package services;

import dao.UserDAO;
import models.User;
public class UserService {
    private static UserService instance;
    private final UserDAO userDAO = new UserDAO();


    public static synchronized UserService getInstance() {

        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }
    public boolean isRegistered(String email, String password){
        User user = userDAO.get(email);
        if(user == null){
            return false;
        }
        return user.getEmail().equals(email) && user.getPassword().equals(password);
    }

    public User get(String email){
        return userDAO.get(email);
    }
}
