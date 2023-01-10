package com.payments.services;

import com.payments.dao.PaymentDAO;
import com.payments.data.DBException;
import com.payments.models.Payment;
import com.payments.models.User;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentService {
    private static PaymentService instance;
    public static synchronized PaymentService getInstance() {

        if (instance == null) {
            instance = new PaymentService();
        }
        return instance;
    }
    private final static PaymentDAO paymentDAO = new PaymentDAO();
    public void save(Payment payment) throws DBException {
        paymentDAO.save(payment);
    }

    public List<Payment> getAllUserPayments(User user) throws DBException {
        return paymentDAO.getPaymentsByUserId(user.getId());
    }
    public static List<Payment> sortByNumber(List<Payment> payment){
        return payment.stream().sorted(Comparator.comparing(Payment::getPayerAccountNumber)).collect(Collectors.toList());
    }
    public static List<Payment> sortByDate(List<Payment> payment){
        return payment.stream().sorted(Comparator.comparing(Payment::getTimeStamp)).collect(Collectors.toList());
    }

    public Payment getById(int paymentId) throws DBException {
       return paymentDAO.get(paymentId);
    }

    public void updateStatus(Payment payment) throws DBException {

        paymentDAO.updateStatus(payment);
    }
}
