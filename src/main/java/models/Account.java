package models;

import java.io.Serializable;
import java.util.Objects;

public class Account implements Serializable {
    private int id;
    private String currency;
    private double balance;
    private Card card;
    private String ownerName;
    private String ownerPhone;
    private String ownerAdress;
    private String postalCode;
    private boolean blocked;

    public Account() {
    }

    public Account(int id, String currency, double balance, Card card, String ownerName, String ownerPhone, String ownerAdress, String postalCode, boolean blocked) {
        this.id = id;
        this.currency = currency;
        this.balance = balance;
        this.card = card;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.ownerAdress = ownerAdress;
        this.postalCode = postalCode;
        this.blocked = blocked;
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

    public String getOwnerAdress() {
        return ownerAdress;
    }

    public void setOwnerAdress(String ownerAdress) {
        this.ownerAdress = ownerAdress;
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
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", currency='" + currency + '\'' +
                ", balance=" + balance +
                ", card=" + card +
                ", ownerName='" + ownerName + '\'' +
                ", ownerPhone='" + ownerPhone + '\'' +
                ", ownerAdress='" + ownerAdress + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", blocked=" + blocked +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Double.compare(account.balance, balance) == 0 && Objects.equals(currency, account.currency) && Objects.equals(card, account.card) && Objects.equals(ownerName, account.ownerName) && Objects.equals(ownerPhone, account.ownerPhone) && Objects.equals(ownerAdress, account.ownerAdress) && Objects.equals(postalCode, account.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, balance, card, ownerName, ownerPhone, ownerAdress, postalCode);
    }
}
