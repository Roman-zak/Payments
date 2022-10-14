package com.payments.payments.controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import services.AccountService;

import java.io.IOException;

@WebServlet(name = "accountStatus", value = "/accountStatus")
public class AccountStatusController extends HttpServlet {
    AccountService accountService = AccountService.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isBlocked = Boolean.parseBoolean(request.getParameter("blocked"));
        String accountNo = request.getParameter("accountNo");
        boolean isChanged = accountService.changeAccountStatus(accountNo);
        if(!isChanged){
            request.getSession().setAttribute("accStatusMessage", "Your account "+accountNo+" status can not be changed right now");
        }
        else if(!isBlocked&&isChanged){
            request.getSession().setAttribute("accStatusMessage", "Your account "+accountNo+" was successfully blocked");
        } else if (isBlocked&&isChanged) {
            request.getSession().setAttribute("accStatusMessage", "Your account "+accountNo+" unblock request was sent to admin.");
        }
        new ProfileController().doGet(request,response);
    }
}
