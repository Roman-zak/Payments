package com.payments.controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.payments.services.UserService;

import java.io.IOException;

@WebServlet(name = "userStatus", value = "/adminPage/userStatus")
public class UserStatusController extends HttpServlet {
    private static UserService userService = UserService.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isBlocked = Boolean.parseBoolean(request.getParameter("blocked"));
        int id = Integer.parseInt(request.getParameter("id"));
        boolean isChanged = userService.changeUserStatus(id);
        if(!isChanged){
            request.getSession().setAttribute("userStatusMessage", "User "+id+" status can not be changed right now");
        }
        else if(!isBlocked&&isChanged){
            request.getSession().setAttribute("userStatusMessage", "User "+id+" was successfully blocked");
        } else if (isBlocked&&isChanged) {
            request.getSession().setAttribute("userStatusMessage", "User "+id+" was successfully unblocked");
        }
        response.sendRedirect("/adminPage");
    }
}
