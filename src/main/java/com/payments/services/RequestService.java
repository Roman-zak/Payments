package com.payments.services;

import com.payments.dao.CardDAO;
import com.payments.dao.RequestDAO;
import com.payments.data.DBException;
import com.payments.models.Account;
import com.payments.models.UnblockAccountRequest;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestService {
    private static RequestService instance;
    private final Logger logger = Logger.getLogger(RequestService.class);
    private static RequestDAO requestDAO = new  RequestDAO();
    private static CardDAO cardDAO = new CardDAO();
    public static synchronized RequestService getInstance() {

        if (instance == null) {
            instance = new RequestService();
        }
        return instance;
    }

    public boolean makeRequest(Account account) {
        UnblockAccountRequest unblockRequest = new UnblockAccountRequest(account);
        try {
            requestDAO.save(unblockRequest);
            return true;
        } catch (DBException e) {
            return false;
        }
    }

    public List<UnblockAccountRequest> getAll() throws DBException {
        return requestDAO.getAll();
    }

    public Map.Entry<List<UnblockAccountRequest>, Integer> getAllWithLimit(int i, int recordsPerPage) throws DBException {
        Map.Entry<List<UnblockAccountRequest>, Integer> accountsEntries= requestDAO.getAllWithLimit(i,recordsPerPage);
        cardDAO.attachCardToAccounts(accountsEntries.getKey().stream().map(r->r.getAccount()).collect(Collectors.toList()));
        return accountsEntries;
    }
}
