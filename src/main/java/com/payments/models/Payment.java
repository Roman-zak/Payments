package com.payments.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Payment implements Serializable {
    private int id;
    private double sum;
    private int payerAccountId;
    private String payerAccountNumber;
    private String recipientAccountNo;
    private String recipientName;
    private PaymentStatus status;
    private LocalDateTime timeStamp;


    public Payment(double sum, int payerAccountId, String recipientAccountNo) {
        this.sum = sum;
        this.payerAccountId = payerAccountId;
        this.recipientAccountNo = recipientAccountNo;
    }

    public Payment(double sum, int payerAccountId, String payerAccountNumber, String recipientAccountNo, String recipientName, PaymentStatus status, LocalDateTime timeStamp) {
        this.sum = sum;
        this.payerAccountId = payerAccountId;
        this.payerAccountNumber = payerAccountNumber;
        this.recipientAccountNo = recipientAccountNo;
        this.recipientName = recipientName;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        Payment payment = (Payment) o;
        return Double.compare(payment.sum, sum) == 0 && Objects.equals(payerAccountId, payment.payerAccountId) && Objects.equals(recipientAccountNo, payment.recipientAccountNo) && Objects.equals(recipientName, payment.recipientName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum, payerAccountId, recipientAccountNo, recipientName);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", sum=" + sum +
                ", payerAccount=" + payerAccountId +
                ", recipientAccountNo='" + recipientAccountNo + '\'' +
                ", recipientName='" + recipientName + '\'' +
                ", status=" + status +
                ", timeStamp=" + timeStamp +
                '}';
    }


    public Payment() {
    }

    public Payment(int id, double sum, int payerAccountId, String recipientAccountNo, String recipientName, PaymentStatus status, LocalDateTime timeStamp) {
        this.id = id;
        this.sum = sum;
        this.payerAccountId = payerAccountId;
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
    @Min(value = 0, message = "sum must be greater than 0")
    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public int getPayerAccountId() {
        return payerAccountId;
    }

    public void setPayerAccountId(int payerAccountId) {
        this.payerAccountId = payerAccountId;
    }
    @NotBlank(message = "field must not be blank")
    @Pattern(regexp = "^[0-9]{8,17}$", message = "must contain from 8 to 17 digits")
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

    public String getPayerAccountNumber() {
        return payerAccountNumber;
    }

    public void setPayerAccountNumber(String payerAccountNumber) {
        this.payerAccountNumber = payerAccountNumber;
    }
}
