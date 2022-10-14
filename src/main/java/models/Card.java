package models;

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

    public int getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(int expMonth) {
        this.expMonth = expMonth;
    }

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

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

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
