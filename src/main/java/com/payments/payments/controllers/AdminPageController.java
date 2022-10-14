package com.payments.payments.controllers;

import db.DBException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Account;
import models.UnblockAccountRequest;
import models.User;
import services.AccountService;
import services.RequestService;
import services.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "adminPage", value = "/adminPage")
public class AdminPageController extends HttpServlet {
    private static RequestService requestService = RequestService.getInstance();
    private static UserService userService = UserService.getInstance();
    private static AccountService accountService = AccountService.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<User> allUsers = userService.getAll();
            List<Account> allAccounts = accountService.getAll();
            List<UnblockAccountRequest> allUnblockRequest = requestService.getAll();
           // int noOfPages =
            int recordsPerPage = 10;
            int page = 1;
            if (request.getParameter("page") != null){
                page = Integer.parseInt(request.getParameter("page"));
            }
            request.getSession().setAttribute("adminPageMode",request.getParameter("adminPageMode"));
            request.getSession().setAttribute("allUsers",allUsers);
            request.getSession().setAttribute("allAccounts",allAccounts);
            request.getSession().setAttribute("allUnblockRequest",allUnblockRequest);
          //  request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("WEB-INF/adminPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}