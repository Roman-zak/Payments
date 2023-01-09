package com.payments.services;

import com.payments.dao.AccountDAO;
import com.payments.dao.CardDAO;
import com.payments.dao.UserAccountDAO;
import com.payments.data.DBCPDataSource;
import com.payments.data.DBException;
import com.payments.models.Account;
import com.payments.models.Card;
import com.payments.models.Role;
import com.payments.models.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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

    public boolean changeAccountStatus(String accountNo, User user) {
        try {
            Account account = accountDAO.getByAccountNo(accountNo);
            if(!account.isBlocked() ||user.getRole().equals(Role.ADMIN)){
                accountDAO.updateStatus(account);
            } else if(user.getRole().equals(Role.USER)){
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

    public Map.Entry<List<Account>, Integer> getAllWithLimit(int i, int recordsPerPage) throws DBException {
        Map.Entry<List<Account>, Integer> accountsEntries= accountDAO.getAllWithLimit(i,recordsPerPage);
        cardDAO.attachCardToAccounts(accountsEntries.getKey());
        return accountsEntries;
    }

    public boolean save(User user, Account account, Card card){
        Connection connection = null;
        try {
            connection = DBCPDataSource.getConnection();
        } catch (DBException e) {
            return false;
        }
        try{
            accountDAO.save(account, connection);
            card.setAccountId(account.getId());
            userAccountDAO.save(user, account, connection);
            cardDAO.save(card, connection);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                return false;
            }
            return false;
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
               return false;
            }
        }
        return true;
    }

    public void deleteById(int accountId, int userId) throws DBException {
        int usersCount = userAccountDAO.getCountOfUsersWithAccount(accountId);
        System.out.println("usersCount"+usersCount);
        if(usersCount>1){
            userAccountDAO.delete(accountId, userId);
        } else if (usersCount==1) {
            Connection con = DBCPDataSource.getConnection();
            try{
                userAccountDAO.delete(accountId, userId, con);
                cardDAO.delete(accountId, con);
                accountDAO.delete(accountId, con);
                con.commit();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    logger.error(ex);
                    throw new DBException("failed to rollback", ex);
                }
                throw new DBException( "failed to delete account",e);
            }finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        logger.error(ex);
                        throw new DBException( "failed to close resources",ex);
                    }
            }
        }
    }
}
