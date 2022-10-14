package models;

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

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

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
}
