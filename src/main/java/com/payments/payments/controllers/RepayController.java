package com.payments.payments.controllers;

import db.DBException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Account;
import models.Payment;
import models.PaymentStatus;
import services.AccountService;
import services.PaymentService;
import services.UserService;

import java.io.IOException;

@WebServlet(name = "repay", value = "/repay")
public class RepayController extends HttpServlet {
    PaymentService paymentService = PaymentService.getInstance();
    AccountService accountService = AccountService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int paymentId = Integer.parseInt(request.getParameter("id"));
        Account payerAccount = null;
        try {
            Payment payment = paymentService.getById(paymentId);
            payerAccount = accountService.getById(payment.getPayerAccountId());
            if (payerAccount.getBalance() < payment.getSum()) {
                System.out.println("witn id "+payment.getPayerAccountId()+" "+payerAccount.getAccountNo()+" balance "+payerAccount.getBalance()+" sum"+payment.getSum() );
                request.getSession().setAttribute("lastPaymentMessage",
                        "Not enough money on balance to perform tis payment. " +
                                "This payment was saved as \"prepared\"");
            }else {
                System.out.println("witn id "+payment.getPayerAccountId()+" "+payerAccount.getAccountNo()+" balance "+payerAccount.getBalance()+" sum"+payment.getSum() );
                double newBalance =payerAccount.getBalance()-payment.getSum();
                payerAccount.setBalance(newBalance);
                accountService.updateAccountBalance(payerAccount,newBalance);
                paymentService.updateStatus(payment);
                payment.setStatus(PaymentStatus.SENT);
                request.getSession().setAttribute("lastPaymentMessage",
                        "Payment was performed successfully!");
            }
            new PaymentsArchiveController().doGet(request, response);
//            response.sendRedirect("/repay");
//            request.getRequestDispatcher("WEB-INF/paymentsArchive.jsp").forward(request, response);
        } catch (DBException e) {
            response.sendError(500, "Payment was not performed, try latter please.");
//            request.getSession().setAttribute("lastPaymentMessage",
//                    "Payment was not performed, try latter please.");
        }


    }
}
