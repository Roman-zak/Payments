package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Payment implements Serializable {
    private int id;
    private double sum;
    private Account payerAccount;
    private String recipientAccountNo;
    private String recipientName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        Payment payment = (Payment) o;
        return Double.compare(payment.sum, sum) == 0 && Objects.equals(payerAccount, payment.payerAccount) && Objects.equals(recipientAccountNo, payment.recipientAccountNo) && Objects.equals(recipientName, payment.recipientName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum, payerAccount, recipientAccountNo, recipientName);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", sum=" + sum +
                ", payerAccount=" + payerAccount +
                ", recipientAccountNo='" + recipientAccountNo + '\'' +
                ", recipientName='" + recipientName + '\'' +
                ", status=" + status +
                ", timeStamp=" + timeStamp +
                '}';
    }

    private PaymentStatus status;
    private LocalDateTime timeStamp;

    public Payment() {
    }

    public Payment(int id, double sum, Account payerAccount, String recipientAccountNo, String recipientName, PaymentStatus status, LocalDateTime timeStamp) {
        this.id = id;
        this.sum = sum;
        this.payerAccount = payerAccount;
        this.recipientAccountNo = recipientAccountNo;
        this.recipientName = recipientName;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public Account getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(Account payerAccount) {
        this.payerAccount = payerAccount;
    }

    public String getRecipientAccountNo() {
        return recipientAccountNo;
    }

    public void setRecipientAccountNo(String recipientAccountNo) {
        this.recipientAccountNo = recipientAccountNo;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
