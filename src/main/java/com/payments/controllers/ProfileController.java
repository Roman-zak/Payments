package com.payments.controllers;

import com.payments.data.DBException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.payments.models.User;
import com.payments.services.AccountService;

import java.io.IOException;

@WebServlet(name = "profile", value = "/profile")
public class ProfileController extends HttpServlet {
    AccountService accountService = AccountService.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       request.getSession().setAttribute("accountsSortMode","balance");
        User user = (User)request.getSession().getAttribute("user");
        try {
            user.setAccounts(accountService.getUserAccounts(user));
        } catch (DBException e) {
            response.sendError(500, "Can not upload your accounts");
        }
        request.getSession().setAttribute("user",user);
        request.getRequestDispatcher("WEB-INF/profile.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
