package com.payments.payments.controllers;

import dao.DAO;
import dao.UserDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Role;
import models.User;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RegisterController", value = "/RegisterController")
public class RegisterController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = null;
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        DAO<User> userDAO = new UserDAO();
        user = new User(email, password, name, surname, Role.USER, false);
        if (email == null || email.isEmpty()) {
            resp.sendError(400, "Email can't be empty");
        } else if (password == null || password.isEmpty()) {
            resp.sendError(400, "Password can't be empty");
        } else {
            userDAO.save(user);
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("/");
        }
    }
}
