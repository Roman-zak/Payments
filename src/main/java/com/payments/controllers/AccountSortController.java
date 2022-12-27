package com.payments.controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "accountSort", value = "/accountSort")
public class AccountSortController extends HttpServlet {
    private final Logger logger = Logger.getLogger(LoginController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("account sort mode"+request.getParameter("accountsSortMode"));
        request.getSession().setAttribute("accountsSortMode", request.getParameter("accountsSortMode"));
        request.getRequestDispatcher("WEB-INF/profile.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println("--------------------------------MODE PASSED "+request.getParameter("accountsSortMode"));
        logger.debug("account sort mode"+request.getParameter("accountsSortMode"));

        request.getSession().setAttribute("accountsSortMode", request.getParameter("accountsSortMode"));
        request.getRequestDispatcher("WEB-INF/profile.jsp").forward(request, response);
        //request.getRequestDispatcher("WEB-INF/profile.jsp").forward(request, response);
    }
}
