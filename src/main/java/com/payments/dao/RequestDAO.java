package com.payments.dao;

import com.payments.data.DBCPDataSource;
import com.payments.data.DBException;
import com.payments.data.Query;
import com.payments.models.Account;
import com.payments.models.UnblockAccountRequest;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestDAO implements DAO<UnblockAccountRequest>{
    private static final Logger logger = Logger.getLogger(RequestDAO.class);
    private static final AccountDAO accountDAO = new AccountDAO();
    private int noOfRecords;
    @Override
    public UnblockAccountRequest get(int id) throws DBException {
        return null;
    }

    @Override
    public List<UnblockAccountRequest> getAll() throws DBException {
        Connection con = DBCPDataSource.getConnection();
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
        Connection con = DBCPDataSource.getConnection();
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

    public Map.Entry<List<UnblockAccountRequest>, Integer> getAllWithLimit(int offset, int noOfRecords) throws DBException {
        String query = "select SQL_CALC_FOUND_ROWS * from request_account limit " + offset + ", " + noOfRecords;
        Connection connection = DBCPDataSource.getConnection();
        List<UnblockAccountRequest> requests = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()){
                UnblockAccountRequest request = new UnblockAccountRequest(rs.getInt("id"),
                        accountDAO.get(rs.getInt("account_id")));
                requests.add(request);
            }

            rs = stmt.executeQuery("SELECT FOUND_ROWS()");

            if (rs.next())
                this.noOfRecords = rs.getInt(1);
            connection.commit();
        } catch (SQLException ex) {
            throw new DBException("Failed to get users", ex);
        }finally {
            try {
                rs.close();
                stmt.close();
                connection.close();
            } catch (SQLException e) {
                throw new DBException("Can not get account id by acc number", e);
            }
        }
        Map<List<UnblockAccountRequest>, Integer> res = new HashMap<>();
        res.put(requests,this.noOfRecords );
        for ( Map.Entry<List<UnblockAccountRequest>, Integer> entry: res.entrySet()){
            return entry;
        }
        return null;
    }
}
