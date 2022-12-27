package com.payments.controllers;

import com.payments.data.DBException;
import com.payments.models.User;
import com.payments.services.AccountService;
import com.payments.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "login", value = "/login")
public class LoginController extends HttpServlet {
    private final Logger logger = Logger.getLogger(LoginController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (email == null || email.isEmpty()) {
            req.getSession().setAttribute("message", "Email can't be empty");
          //  resp.sendError(400, "Email can't be empty");
        } else if (password == null || password.isEmpty()) {
            req.getSession().setAttribute("message", "Password can't be empty");
           // resp.sendError(400, "Password can't be empty");
        } else {
            try{
                UserService userService = UserService.getInstance();
                AccountService accountService = AccountService.getInstance();
                if(userService.isRegistered(email, password)){
                    User user = userService.get(email);
                    user.setAccounts(accountService.getUserAccounts(user));
                    req.getSession().setAttribute("user", user);
                    resp.sendRedirect("/");
                } else {
                    req.getSession().setAttribute("message", "Wrong login data.");
                    resp.sendRedirect("/login");
                    //req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
                }
            } catch (DBException e){
                logger.warn("Incorrect password or login",e);
                logger.debug("start forwarding to login page");
                req.getSession().setAttribute("message", "Wrong login data.");
                resp.sendRedirect("/login");
            }
        }
    }
}
