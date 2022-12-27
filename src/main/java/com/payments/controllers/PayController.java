package com.payments.controllers;

import com.payments.data.DBException;
import com.payments.models.Account;
import com.payments.models.Payment;
import com.payments.models.PaymentStatus;
import com.payments.services.AccountService;
import com.payments.services.PaymentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Set;

@WebServlet(name = "Pay", value = "/pay")
public class PayController extends HttpServlet {
    private static final org.apache.log4j.Logger logger = Logger.getLogger(PayController.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Payment payment = null;
        double sum = Double.parseDouble(request.getParameter("sum"));
        int payerAccountId = Integer.parseInt(request.getParameter("payer_id"));
        String recipientAccountNo = request.getParameter("recipientAccountNo");

        PaymentService paymentService = new PaymentService();

        payment = new Payment(sum, payerAccountId, recipientAccountNo);

        Validator validator = null;
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            validator = validatorFactory.getValidator();
        }catch (Exception e){
            System.out.println(e);
        }
        Set<ConstraintViolation<Payment>> constraintViolations = validator.validate(payment);
        if (!constraintViolations.isEmpty()) {
            String errors = "<ul>";
            for (ConstraintViolation<Payment> constraintViolation : constraintViolations) {
                errors += "<li>" + constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage()
                        + "</li>";
            }
            errors += "</ul>";
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/").forward(request, response);
        }

        AccountService accountService = AccountService.getInstance();
        Account payerAccount = null;
        Account recipientAccount = null;
        try {
            payerAccount = accountService.getById(payerAccountId);
            logger.debug("payer account: "+payerAccount);
            if(payerAccount.getAccountNo().equals(recipientAccountNo)){
                logger.debug("payer and recipient are the same");
                request.getSession().setAttribute("lastPaymentMessage",
                        "Payer and recipient accounts must differ.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }
            recipientAccount = accountService.getByAccountNo(recipientAccountNo);
        } catch (DBException e) {
            response.sendError(403, "There are no account with this number.");
        }

        if (payerAccount.getBalance() < sum) {
            logger.debug("payer "+payerAccount+" not enough money");
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
            logger.debug("before save");
            paymentService.save(payment);
            logger.debug("after save");
        } catch (DBException e) {
            request.getSession().setAttribute("lastPaymentMessage",
                    "Payment was not performed, try latter please.");
        }
        logger.debug("before sendRedirect");
        response.sendRedirect("/");
        logger.debug("after sendRedirect");
        //request.getRequestDispatcher("/").forward(request, response);
    }
}
