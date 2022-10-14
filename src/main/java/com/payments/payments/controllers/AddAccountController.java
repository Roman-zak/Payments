package com.payments.payments.controllers;

import dao.DAO;
import dao.UserDAO;
import db.DBException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Account;
import models.Card;
import models.Role;
import models.User;
import services.AccountService;
import services.CardService;

import java.io.IOException;

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
        int hasId = 0;
        try {
            hasId = accountService.getIdByNumber(accountNo);
            if(hasId>0){
                accountService.saveExistingAccountById(((User)request.getSession().getAttribute("user")).getId(),hasId);
                account = accountService.getById(hasId);
                ((User)request.getSession().getAttribute("user")).getAccounts().add(account);
                request.getRequestDispatcher("WEB-INF/profile.jsp").forward(request, response);
                return;
            }
        } catch (DBException e) {
            response.sendError(403, "Was unable to add this account.");
        }
        String currency = request.getParameter("currency");
        String ownerName = request.getParameter("ownerName");
        String ownerPhone = request.getParameter("ownerPhone");
        String ownerAddress = request.getParameter("ownerAddress");
        System.out.println(ownerAddress);
        String postalCode = request.getParameter("postalCode");

        String cardNo = request.getParameter("cardnumber");
        int expMonth = Integer.parseInt(request.getParameter("expMonth"));
        int expYear = Integer.parseInt(request.getParameter("expYear"));
        String cvc = request.getParameter("cvc");

        card = new Card(cardNo, cvc, expMonth, expYear);
        account = new Account(accountNo, currency,0.0,card, ownerName,ownerPhone, ownerAddress, postalCode);
        if (expMonth <1||expMonth>12) {
            response.sendError(400, "Month value is incorrect");
        } else {
            try {
                accountService.save((User)request.getSession().getAttribute("user"),account);
                card.setAccountId(account.getId());
                cardService.save(card);
            } catch (DBException e) {
                response.sendError(403, "Was unable to add this account.");
            }
            ((User)request.getSession().getAttribute("user")).getAccounts().add(account);
           // response.sendRedirect("WEB-INF/profile.jsp");
            request.getRequestDispatcher("WEB-INF/profile.jsp").forward(request, response);
        }

    }
}
