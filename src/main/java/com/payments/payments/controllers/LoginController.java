package com.payments.payments.controllers;

import dao.DAO;
import dao.UserDAO;
import db.DBException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.User;
import services.UserService;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(name = "LoginController", value = "/LoginController")
public class LoginController extends HttpServlet {
    private final Logger logger = Logger.getLogger(LoginController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
        logger.debug("get login");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        DAO<User> userDAO = new UserDAO();
        if (email == null || email.isEmpty()) {
            resp.sendError(400, "Email can't be empty");
        } else if (password == null || password.isEmpty()) {
            resp.sendError(400, "Password can't be empty");
        } else {
            try{
                UserService userService = UserService.getInstance();
                if(userService.isRegistered(email, password)){
                    User user = userService.get(email);
                    req.getSession().setAttribute("user", user);
                    resp.sendRedirect("/");
                } else {
                    req.getSession().setAttribute("message", "Wrong login data.");
                    req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
                }
            } catch (DBException e){
                logger.warn("Incorrect password or login");
                logger.debug("start forwarding to login page");
                req.getSession().setAttribute("message", "Wrong login data.");
                req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);

             //   resp.sendError(403, "Incorrect password or login");
            }
        }
    }
}
