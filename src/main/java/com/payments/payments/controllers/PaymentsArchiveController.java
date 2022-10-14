package com.payments.payments.controllers;

import db.DBException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Payment;
import models.User;
import services.PaymentService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "paymentsArchive", value = "/paymentsArchive")
public class PaymentsArchiveController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Payment> payments = new ArrayList<>();
        PaymentService paymentService = PaymentService.getInstance();
        try {
            payments = paymentService.getAllUserPayments((User)request.getSession().getAttribute("user"));
        } catch (DBException e) {
            response.sendError(500, "Currently can not upload your payments, please, try latter");
        }
        request.getSession().setAttribute("payments",payments);
        request.getSession().setAttribute("paymentsSortMode", "date");

        request.getRequestDispatcher("WEB-INF/paymentsArchive.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
