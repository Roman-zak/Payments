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

import java.io.IOException;
import java.util.Set;

@WebServlet(name = "addAccount", value = "/addAccount")
public class AddAccountController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/addAccount.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
//        if(sExpMonth==null | !sExpMonth.matches("^(0?[1-9]|1[012])$")){
//            response.sendError(403, "Proper month value is required.");
//        }
//        if(sExpYear==null | !sExpYear.matches("^\\d{4}$")){
//            response.sendError(403, "Proper year value is required.");
//        }
        int expMonth = Integer.parseInt(sExpMonth);
        int expYear = Integer.parseInt(sExpYear);
        String cvc = request.getParameter("cvc");
//        if(accountNo.isEmpty()||accountNo==null || accountNo.length()!=16){
//            response.sendError(403, "Proper account number is required.");
//        }
//        if(ownerName.isEmpty()||ownerName==null){
//            response.sendError(403, "Proper name is required.");
//        }
//        if(ownerPhone.isEmpty()||ownerPhone==null || !ownerPhone.matches("^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$")){
//            response.sendError(403, "Proper phone number in format \"+380993208889\" is required.");
//        }
//        if(ownerAddress.isEmpty()||ownerAddress==null){
//            response.sendError(403, "Proper address is required.");
//        }
//        if(postalCode.isEmpty()||postalCode==null||!postalCode.matches("[0-9]{5}")){
//            response.sendError(403, "Proper postal code is required.");
//        }
//        if(cardNo == null || !cardNo.matches("^4[0-9]{12}(?:[0-9]{3})?$")){
//            response.sendError(403, "Proper card number is required.");
//        }
//        if(cvc == null || !cvc.matches("[0-9]{3}"));
        card = new Card(cardNo, cvc, expMonth, expYear);
        account = new Account(accountNo, currency,0.0,card, ownerName,ownerPhone, ownerAddress, postalCode);
        Validator validator = null;
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            validator = validatorFactory.getValidator();
        }catch (Exception e){
            System.out.println(e);
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
}
