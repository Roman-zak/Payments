package com.payments.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.Objects;

public class Card implements Serializable {
    private int id;
    private int accountId;
    private String cardNo;
    private String cvc;
    private int expMonth;

    public Card(int id, int accountId, String cardNo, String cvc, int expMonth, int expYear) {
        this.id = id;
        this.accountId = accountId;
        this.cardNo = cardNo;
        this.cvc = cvc;
        this.expMonth = expMonth;
        this.expYear = expYear;
    }
    @NotEmpty(message = "must not be empty")
    @Pattern(regexp = "^(0?[1-9]|1[012])$", message = "must be in format \"mm\"")
    public int getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(int expMonth) {
        this.expMonth = expMonth;
    }
    @NotEmpty(message = "must not be empty")
    @Pattern(regexp = "^[0-9]{4}$", message = "must be proper")
    public int getExpYear() {
        return expYear;
    }

    public void setExpYear(int expYear) {
        this.expYear = expYear;
    }

    private int expYear;

    public Card() {
    }

    public Card(int accountId, String cardNo, String cvc, int expMonth, int expYear) {
        this.accountId = accountId;
        this.cardNo = cardNo;
        this.cvc = cvc;
        this.expMonth = expMonth;
        this.expYear = expYear;
    }
    public Card( String cardNo, String cvc, int expMonth, int expYear) {
        this.cardNo = cardNo;
        this.cvc = cvc;
        this.expMonth = expMonth;
        this.expYear = expYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @NotBlank(message = "must not be blank")
    @Pattern(regexp = "(^4[0-9]{12}(?:[0-9]{3})?$)" +
            "|(^(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}$)" +
            "|(3[47][0-9]{13})|(^3(?:0[0-5]|[68][0-9])[0-9]{11}$)" +
            "|(^6(?:011|5[0-9]{2})[0-9]{12}$)" +
            "|(^(?:2131|1800|35\\d{3})\\d{11}$)", message = "must be proper")
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    @NotBlank(message = "must not be blank")
    @Pattern(regexp = "^[0-9]{3}$", message = "must contain of 3 numbers")
    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return expMonth == card.expMonth && expYear == card.expYear && cardNo.equals(card.cardNo) && cvc.equals(card.cvc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNo, cvc, expMonth, expYear);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardNo='" + cardNo + '\'' +
                ", cvc='" + cvc + '\'' +
                ", expMonth=" + expMonth +
                ", expYear=" + expYear +
                '}';
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
