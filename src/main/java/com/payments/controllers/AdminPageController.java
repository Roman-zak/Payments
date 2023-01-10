package com.payments.controllers;

import com.payments.data.DBException;
import com.payments.models.Account;
import com.payments.models.UnblockAccountRequest;
import com.payments.models.User;
import com.payments.services.AccountService;
import com.payments.services.RequestService;
import com.payments.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "adminPage", value = "/adminPage")
public class AdminPageController extends HttpServlet {
    private static RequestService requestService = RequestService.getInstance();
    private static UserService userService = UserService.getInstance();
    private static AccountService accountService = AccountService.getInstance();
    private final Logger logger = Logger.getLogger(AdminPageController.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("adminPageMode")!=null){
            request.getSession().setAttribute("adminPageMode",request.getParameter("adminPageMode"));
        }else {
            request.getSession().setAttribute("adminPageMode","users");
        }
        int page = 1;
        int recordsPerPage = 5;
        int noOfRecords=0;
        logger.debug("page - "+request.getParameter("page"));
        if (request.getParameter("page") != null){
            page = Integer.parseInt(String.valueOf(request.getParameter("page"))) ;
        }
        if(request.getSession().getAttribute("adminPageMode").equals("users")){
            Map.Entry<List<User>, Integer> usersEntry;
            List<User> users = null;
            try {
                usersEntry = userService.getAllWithLimit(
                        (page - 1) * recordsPerPage,
                        recordsPerPage);
                users = usersEntry.getKey();
                noOfRecords = usersEntry.getValue();
                request.getSession().setAttribute("allUsers",users);
            } catch (DBException e) {
                logger.error(e);
                response.sendError(500,"Can not get users");
            }

        } else if (request.getSession().getAttribute("adminPageMode").equals("accounts")) {
            Map.Entry<List<Account>, Integer> accountsEntry;
            List<Account> accounts = null;
            try {
                accountsEntry = accountService.getAllWithLimit(
                        (page - 1) * recordsPerPage,
                        recordsPerPage);
                accounts = accountsEntry.getKey();
                 logger.debug("gooten all:"+accounts.size());
                noOfRecords = accountsEntry.getValue();
                request.getSession().setAttribute("allAccounts",accounts);
            } catch (DBException e) {
                logger.error(e);
                response.sendError(500,"Can not get accounts");
            }
        } else if (request.getSession().getAttribute("adminPageMode").equals("unblockRequests")) {
            Map.Entry<List<UnblockAccountRequest>, Integer> requestsEntry;
            List<UnblockAccountRequest> requests = null;
            try {
                requestsEntry = requestService.getAllWithLimit(
                        (page - 1) * recordsPerPage,
                        recordsPerPage);
                requests = requestsEntry.getKey();
                noOfRecords = requestsEntry.getValue();
                request.getSession().setAttribute("allUnblockRequest",requests);
            } catch (DBException e) {
                logger.error(e);
                response.sendError(500,"Can not get unblock requests");
            }
        }
        int noOfPages = (int)Math.ceil(Double.valueOf(noOfRecords) / recordsPerPage);
        request.getSession().setAttribute("noOfPages", noOfPages);
        request.getSession().setAttribute("currentPage", page);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.getRequestDispatcher("WEB-INF/adminPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
