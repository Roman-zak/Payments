package com.payments.controllers;

import com.payments.data.DBException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.payments.models.Account;
import com.payments.models.Payment;
import com.payments.models.PaymentStatus;
import com.payments.services.AccountService;
import com.payments.services.PaymentService;
import org.apache.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "repay", value = "/repay")
public class RepayController extends HttpServlet {
    Logger logger = Logger.getLogger(RepayController.class);
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
                 logger.debug("witn id "+payment.getPayerAccountId()+" "+payerAccount.getAccountNo()+" balance "+payerAccount.getBalance()+" sum"+payment.getSum() );
                request.getSession().setAttribute("lastPaymentMessage",
                        "Not enough money on balance to perform tis payment. " +
                                "This payment was saved as \"prepared\"");
            }else {
                 logger.debug("witn id "+payment.getPayerAccountId()+" "+payerAccount.getAccountNo()+" balance "+payerAccount.getBalance()+" sum"+payment.getSum() );
                double newBalance =payerAccount.getBalance()-payment.getSum();
                payerAccount.setBalance(newBalance);
                accountService.updateAccountBalance(payerAccount,newBalance);
                paymentService.updateStatus(payment);
                payment.setStatus(PaymentStatus.SENT);
                request.getSession().setAttribute("lastPaymentMessage",
                        "Payment was performed successfully!");
            }
            response.sendRedirect("/paymentsArchive");
        } catch (DBException e) {
            response.sendError(500, "Payment was not performed, try latter please.");
        }


    }
}
