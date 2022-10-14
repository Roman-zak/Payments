package dao;

import db.C3p0DataSource;
import db.DBException;
import db.Query;
import models.Account;
import models.Payment;
import models.UnblockAccountRequest;
import org.apache.log4j.Logger;
import services.RequestService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDAO implements DAO<UnblockAccountRequest>{
    private static final Logger logger = Logger.getLogger(RequestDAO.class);
    private static final AccountDAO accountDAO = new AccountDAO();
    @Override
    public UnblockAccountRequest get(int id) throws DBException {
        return null;
    }

    @Override
    public List<UnblockAccountRequest> getAll() throws DBException {
        Connection con = C3p0DataSource.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<UnblockAccountRequest> unblockAccountRequests = new ArrayList<>();
        try {
            preparedStatement = con.prepareStatement(Query.REQUEST_ACCOUNT_GET_ALL);
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                int id=0;
                int accountId=0;
                while (resultSet.next()) {
                    int k=1;
                    id = resultSet.getInt(k++);
                    accountId = resultSet.getInt(k);
                    Account account = accountDAO.get(accountId);
                    UnblockAccountRequest unblockAccountRequest = new UnblockAccountRequest(id, account);
                    unblockAccountRequests.add(unblockAccountRequest);
                }
            }
            con.commit();
            return unblockAccountRequests;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new DBException("Can not get all requests", e);
            }
            throw new DBException("Can not get all requests", e);
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
                con.close();
            } catch (SQLException e) {
                throw new DBException("Can not get all accounts", e);
            }
        }
    }

    @Override
    public void save(UnblockAccountRequest unblockRequest) throws DBException {
        Connection con = C3p0DataSource.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(Query.ACCOUNT_UNBLOCK_REQUEST_INSERT, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            preparedStatement.setInt(k, unblockRequest.getAccount().getId());

            int count = preparedStatement.executeUpdate();
            logger.debug("effected rows: "+count);
            setGeneratedUnblockAccountRequestId(unblockRequest, preparedStatement );
            con.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw  new DBException("Failed to rollback", ex);
            }
            throw  new DBException("Failed to save unblockRequest", e);
        }
    }
    private void setGeneratedUnblockAccountRequestId(UnblockAccountRequest unblockRequest, PreparedStatement stmt) throws DBException {
        ResultSet generatedKeys = null;
        try {
            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                unblockRequest.setId(generatedKeys.getInt(1));
            }
            else {
                throw new DBException("Creating unblockRequest failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new DBException("Creating unblockRequest failed, no ID obtained.", e);
        }
    }

    @Override
    public void update(UnblockAccountRequest unblockAccountRequest, String[] params) {

    }

    @Override
    public void delete(UnblockAccountRequest unblockAccountRequest) {

    }
}
