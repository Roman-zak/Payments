package com.payments.payments.controllers;

import dao.DAO;
import dao.UserDAO;
import db.DBException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.*;
import services.AccountService;
import services.PaymentService;

import java.io.IOException;

@WebServlet(name = "Pay", value = "/pay")
public class PayController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Payment payment = null;
        double sum = Double.valueOf(request.getParameter("sum"));
        int payerAccountId = Integer.parseInt(request.getParameter("payer_id"));
        String recipientAccountNo = request.getParameter("recipientAccountNo");

        PaymentService paymentService = new PaymentService();

        payment = new Payment(sum, payerAccountId, recipientAccountNo);
        AccountService accountService = AccountService.getInstance();
        Account payerAccount = null;
        Account recipientAccount = null;
        try {
            payerAccount = accountService.getById(payerAccountId);

            if(payerAccount.getAccountNo().equals(recipientAccountNo)){
                request.getSession().setAttribute("lastPaymentMessage",
                        "Payer and recipient accounts must differ.");
                request.getRequestDispatcher("/").forward(request, response);
                return;
            }
            recipientAccount = accountService.getByAccountNo(recipientAccountNo);
        } catch (DBException e) {
            response.sendError(403, "There are no account with this number.");
        }

        if (payerAccount.getBalance() < sum) {
            payment.setStatus(PaymentStatus.PREPARED);
            request.getSession().setAttribute("lastPaymentMessage",
                    "Not enough money on balance to perform tis payment. " +
                            "This payment was saved as \"prepared\"");
        } else {
            double newPayerBalance =payerAccount.getBalance()-sum;
            double newRecipientBalance =0;
            if(recipientAccount!=null){
                newRecipientBalance =recipientAccount.getBalance()+sum;
            }


            try {
                accountService.updateAccountBalance(payerAccount,newPayerBalance);
                payerAccount.setBalance(newPayerBalance);
                if(recipientAccount!=null){
                    accountService.updateAccountBalance(recipientAccount,newRecipientBalance);
                    recipientAccount.setBalance(newRecipientBalance);
                }
            } catch (DBException e) {
                request.getSession().setAttribute("lastPaymentMessage",
                        "Can not perform payment, try latter please.");
            }
            payment.setStatus(PaymentStatus.SENT);
            request.getSession().setAttribute("lastPaymentMessage",
                    "Payment was performed successfully!");
        }
        try {
            paymentService.save(payment);
        } catch (DBException e) {
            request.getSession().setAttribute("lastPaymentMessage",
                    "Payment was not performed, try latter please.");
        }
        response.sendRedirect("/");
        //request.getRequestDispatcher("/").forward(request, response);
    }
}
