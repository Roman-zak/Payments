package com.payments.controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "paymentsSort", value = "/paymentsSort")
public class PaymentsSortController extends HttpServlet {
    private final Logger logger = Logger.getLogger(LoginController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("payments sort mode"+request.getParameter("paymentsSortMode"));
        request.getSession().setAttribute("paymentsSortMode", request.getParameter("paymentsSortMode"));
        request.getRequestDispatcher("WEB-INF/paymentsArchive.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
