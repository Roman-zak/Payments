package dao;

import db.C3p0DataSource;
import db.DBException;
import db.Query;
import models.Account;
import models.Payment;
import models.PaymentStatus;
import models.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO implements DAO<Payment>{
    private final Logger logger = Logger.getLogger(PaymentDAO.class);

    @Override
    public Payment get(int id) throws DBException {
        Payment payment = new Payment();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = C3p0DataSource.getConnection();
            preparedStatement = con.prepareStatement(Query.PAYMENT_GET_BY_ID);
            preparedStatement.setInt(1,id);
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                int k;
                if (resultSet.next()) {
                    k=1;
                    payment.setId(resultSet.getInt(k++));
                    payment.setPayerAccountId(resultSet.getInt(k++));
                    payment.setPayerAccountNumber(resultSet.getString(k++));
                    payment.setSum(resultSet.getDouble(k++));
                    payment.setRecipientAccountNo(resultSet.getString(k++));
                    payment.setTimeStamp(resultSet.getTimestamp(k++).toLocalDateTime());
                    payment.setStatus(PaymentStatus.values()[resultSet.getInt(k)]);
                }
            }
            con.commit();
            return payment;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new DBException("Can't get payment from database", e);
            }
            throw new DBException("Can't get payment from database", e);
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
                con.close();
            } catch (SQLException e){
                throw new DBException("Can't get payment from database", e);
            }
        }
    }

    @Override
    public List<Payment> getAll() {
        return null;
    }

    @Override
    public void save(Payment payment) throws DBException {
        Connection con = C3p0DataSource.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(Query.PAYMENT_CREATE, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            preparedStatement.setDouble(k++, payment.getSum());
            preparedStatement.setInt(k++, payment.getPayerAccountId());
            preparedStatement.setString(k++, payment.getRecipientAccountNo());
            preparedStatement.setString(k++, null);
            preparedStatement.setInt(k, payment.getStatus().ordinal());
            int count = preparedStatement.executeUpdate();
            logger.debug("effected rows: "+count);
            setGeneratedPaymentId( payment, preparedStatement );
            con.commit();
            preparedStatement.close();
        } catch (SQLException e) {
             logger.warn(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw  new DBException("Failed to rollback", ex);
            }
            throw  new DBException("Failed to save payment", e);
        }
    }
    private void setGeneratedPaymentId(Payment payment, PreparedStatement stmt) throws DBException {
        ResultSet generatedKeys = null;
        try {
            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                payment.setId(generatedKeys.getInt(1));
            }
            else {
                throw new DBException("Creating payment failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new DBException("Creating payment failed, no ID obtained.", e);
        }

    }

    @Override
    public void update(Payment payment, String[] params) {

    }

    @Override
    public void delete(Payment payment) {

    }

    public List<Payment> getPaymentsByUserId(int id) throws DBException {
        List<Payment> payments = new ArrayList<>();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = C3p0DataSource.getConnection();
            preparedStatement = con.prepareStatement(Query.PAYMENT_GET_BY_USER_ID);
            preparedStatement.setInt(1,id);
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                int k;
                while (resultSet.next()) {
                    k=1;
                    Payment payment = new Payment();
                    payment.setId(resultSet.getInt(k++));
                    payment.setPayerAccountId(resultSet.getInt(k++));
                    payment.setPayerAccountNumber(resultSet.getString(k++));
                    payment.setSum(resultSet.getDouble(k++));
                    payment.setRecipientAccountNo(resultSet.getString(k++));
                    payment.setTimeStamp(resultSet.getTimestamp(k++).toLocalDateTime());
                    payment.setStatus(PaymentStatus.values()[resultSet.getInt(k)]);
                    payments.add(payment);
                }
            }
            con.commit();
            return payments;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new DBException("Can't get payments from database", e);
            }
            throw new DBException("Can't get payments from database", e);
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
                con.close();
            } catch (SQLException e){
                throw new DBException("Can't get Users from database", e);
            }

        }
    }

    public void updateStatus(Payment payment) throws DBException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = C3p0DataSource.getConnection();
            preparedStatement = con.prepareStatement(Query.UPDATE_PAYMENT_STATUS_BY_PAYMENT_ID);
            if(payment.getStatus()==PaymentStatus.SENT){
                preparedStatement.setInt(1, 0);
            } else{
                preparedStatement.setInt(1, 1);
            }
            preparedStatement.setInt(2, payment.getId());
            preparedStatement.executeUpdate();
            con.commit();

        } catch (SQLException e) {
            logger.warn(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new DBException("Can not change payment status", e);
            }
            throw new DBException("Can not change payment status", e);
        } finally {
            try {
                preparedStatement.close();
                con.close();
            } catch (SQLException e) {
                throw new DBException("Can not change payment status", e);
            }
        }
    }
}
