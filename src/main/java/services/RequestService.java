package services;

import dao.RequestDAO;
import db.DBException;
import models.Account;
import models.UnblockAccountRequest;
import org.apache.log4j.Logger;

import java.util.List;

public class RequestService {
    private static RequestService instance;
    private final Logger logger = Logger.getLogger(RequestService.class);
    private static RequestDAO requestDAO = new  RequestDAO();
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
}
