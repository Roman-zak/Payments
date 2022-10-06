package com.payments.payments.controllers;

import dao.DAO;
import dao.UserDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(name = "LoginController", value = "/LoginController")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String email = req.getParameter("email");
//        String password = req.getParameter("password");
//        String url = "jdbc:mysql://localhost/test";
//        DAO<User> userDAO = new UserDAO();
//        try {
////            Class.forName("com.mysql.jdbc.Driver");
////            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/paymentsdb", "root", "52798");
////            if(conn!=null){
////                System.out.println(conn);
////            } else{
////                System.out.println("((((((((((((((((((((((((:");
////            }
//            userDAO.save(new User());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        if (email == null || email.isEmpty()) {
//            resp.sendError(400, "Email can't be empty");
//        } else if (password == null || password.isEmpty()) {
//            resp.sendError(400, "Password can't be empty");
//        } else {
//            req.getSession().setAttribute("userEmail", email);
//            resp.sendRedirect("/");
//        }
    }
}
