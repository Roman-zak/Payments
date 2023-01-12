package com.payments.controllers;

import com.payments.data.DBException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.payments.models.Account;
import com.payments.models.Card;
import com.payments.models.User;
import com.payments.services.AccountService;
import com.payments.services.CardService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Set;

@WebServlet(name = "account", value = "/account")
public class AccountController extends HttpServlet {
    Logger logger = Logger.getLogger(AccountController.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/addAccount.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("method")!=null && equals("delete")){
            doDelete(request,response);
            return;
        }
        AccountService accountService = AccountService.getInstance();
        CardService cardService = CardService.getInstance();
        Account account = null;
        Card card = null;
        String accountNo = request.getParameter("accountNo");
        String currency = request.getParameter("currency");
        String ownerName = request.getParameter("ownerName");
        String ownerPhone = request.getParameter("ownerPhone");
        String ownerAddress = request.getParameter("ownerAddress");
        String postalCode = request.getParameter("postalCode");
        String cardNo = request.getParameter("cardnumber");
        String sExpMonth = request.getParameter("expMonth");
        String sExpYear = request.getParameter("expYear");

        int expMonth = Integer.parseInt(sExpMonth);
        int expYear = Integer.parseInt(sExpYear);
        String cvc = request.getParameter("cvc");
        card = new Card(cardNo, cvc, expMonth, expYear);
        account = new Account(accountNo, currency,0.0,card, ownerName,ownerPhone, ownerAddress, postalCode);
        Validator validator = null;
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            validator = validatorFactory.getValidator();
        }catch (Exception e){
            logger.error(e);
        }
        Set<ConstraintViolation<Account>> accountConstraintViolations = validator.validate(account);
        Set<ConstraintViolation<Card>> cardConstraintViolations = validator.validate(card);
        if (!accountConstraintViolations.isEmpty() || !cardConstraintViolations.isEmpty()) {
            String errors = "<ul>";
            for (ConstraintViolation<Account> constraintViolation : accountConstraintViolations) {
                errors += "<li>" + constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage()
                        + "</li>";
            }
            for (ConstraintViolation<Card> constraintViolation : cardConstraintViolations) {
                errors += "<li>" + constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage()
                        + "</li>";
            }
            errors += "</ul>";
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("WEB-INF/addAccount.jsp").forward(request, response);
        } else{
            int hasId = 0;
            try {
                hasId = accountService.getIdByNumber(accountNo);
                if(hasId>0){
                    accountService.saveExistingAccountById(((User)request.getSession().getAttribute("user")).getId(),hasId);
                    account = accountService.getById(hasId);
                    ((User)request.getSession().getAttribute("user")).getAccounts().add(account);
                    request.getRequestDispatcher("WEB-INF/profile.jsp").forward(request, response);
                    response.sendRedirect("/profile");
                } else{
                    boolean saved = accountService.save((User)request.getSession().getAttribute("user"),account, card);
                    if(!saved){
                        response.sendError(403, "Was unable to add this account.");
                    }
                    ((User)request.getSession().getAttribute("user")).getAccounts().add(account);
                    response.sendRedirect("/profile");
                }
            } catch (DBException e) {
                response.sendError(403, "Was unable to add this account.");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int accId = Integer.parseInt(req.getParameter("accountId"));
        AccountService accountService = AccountService.getInstance();
        int userId =  ((User)req.getSession().getAttribute("user")).getId();
        try {
            accountService.deleteById(accId,userId);
            resp.sendRedirect("/profile");
        } catch (DBException e) {
            resp.sendError(403, "Was unable to add delete account.");
        }

    }
}
