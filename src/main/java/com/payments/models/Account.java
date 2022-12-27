package com.payments.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.Objects;

public class Account implements Serializable {
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNo='" + accountNo + '\'' +
                ", currency='" + currency + '\'' +
                ", balance=" + balance +
                ", card=" + card +
                ", ownerName='" + ownerName + '\'' +
                ", ownerPhone='" + ownerPhone + '\'' +
                ", ownerAddress='" + ownerAddress + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", blocked=" + blocked +
                '}';
    }

    private int id;
    private String accountNo;
    private String currency;
    private double balance;
    private Card card;
    private String ownerName;
    private String ownerPhone;
    private String ownerAddress;
    private String postalCode;
    private boolean blocked;

    public Account() {
    }

    public Account(String accountNo, String currency, double balance, Card card, String ownerName, String ownerPhone, String ownerAddress, String postalCode) {
        this.accountNo = accountNo;
        this.currency = currency;
        this.balance = balance;
        this.card = card;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.ownerAddress = ownerAddress;
        this.postalCode = postalCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @NotBlank(message = "field must not be blank")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    @Min(0)
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
    @NotBlank
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    @NotEmpty(message = "field must not be blank")
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", message = "must be proper and include country code")
    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }
    @NotBlank(message = "field must not be blank")
    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }
    @NotBlank(message = "field must not be blank")
    @Pattern(regexp = "[0-9]{5}", message = "must consist of 5 numbers")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Double.compare(account.balance, balance) == 0 && Objects.equals(currency, account.currency) && Objects.equals(card, account.card) && Objects.equals(ownerName, account.ownerName) && Objects.equals(ownerPhone, account.ownerPhone) && Objects.equals(ownerAddress, account.ownerAddress) && Objects.equals(postalCode, account.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, balance, card, ownerName, ownerPhone, ownerAddress, postalCode);
    }
@NotBlank(message = "field must not be blank")
@Pattern(regexp = "[0-9]{8,17}", message = "must contain from 8 to 17 digits")
    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
}
