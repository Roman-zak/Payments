package services;

import dao.AccountDAO;
import dao.CardDAO;
import dao.UserAccountDAO;
import db.DBException;
import models.Account;
import models.User;
import org.apache.log4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AccountService {

    private static final AccountDAO accountDAO = new AccountDAO();
    private static final CardDAO cardDAO = new CardDAO();
    private static final UserAccountDAO userAccountDAO = new UserAccountDAO();
    private static final RequestService requestService = RequestService.getInstance();
    private static final Logger logger = Logger.getLogger(AccountService.class);

    private static AccountService instance;
    public static synchronized AccountService getInstance() {

        if (instance == null) {
            instance = new AccountService();
        }
        return instance;
    }
    public void save(User user, Account account) throws DBException {
        accountDAO.save(account);
        userAccountDAO.save(user, account);
    }

    public List<Account> getUserAccounts(User user) throws DBException {
        List<Account> accounts = accountDAO.getAllOfUser(user);
        cardDAO.attachCardToAccounts(accounts);
        return accounts;
    }

    public Account getById(int id) throws DBException {
        return (Account)accountDAO.get(id);
    }

    public void updateAccountBalance(Account payerAccount, double newBalance) throws DBException {
        accountDAO.updateBalance(payerAccount,newBalance);
    }
//return account id, if it already exists in dataBase, otherwise return -1
    public int getIdByNumber(String accountNo) throws DBException {
        Integer res = accountDAO.getIdByAccountNo(accountNo);
        if(res!=null){
            return res;
        }
        return -1;
    }

    public void saveExistingAccountById(int user_id, int account_id) throws DBException {
        userAccountDAO.save(user_id,account_id);
    }

    public static List<Account> sortByNumber(List<Account> accounts){
       return accounts.stream().sorted(Comparator.comparing(Account::getAccountNo)).collect(Collectors.toList());
    }
    public static List<Account> sortByName(List<Account> accounts){
        return accounts.stream().sorted(Comparator.comparing(Account::getOwnerName)).collect(Collectors.toList());
    }
    public static List<Account> sortByBalance(List<Account> accounts){
        return accounts.stream().sorted((a1, a2)->Double.valueOf(a2.getBalance()).compareTo(a1.getBalance())).collect(Collectors.toList());
    }

    public Account getByAccountNo(String recipientAccountNo) throws DBException {
        return accountDAO.getByAccountNo(recipientAccountNo);
    }

    public boolean changeAccountStatus(String accountNo) {
        try {
            Account account = accountDAO.getByAccountNo(accountNo);
            if(!account.isBlocked()){
                accountDAO.updateStatus(account);
            } else{
                requestService.makeRequest(account);
            }
        } catch (DBException e) {
            logger.warn("Can not change status", e);
            return false;
        }
        return true;
    }

    public List<Account> getAll() throws DBException {
        return accountDAO.getAll();
    }
}
