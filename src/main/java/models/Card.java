package models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Card implements Serializable {
    private int id;
    private String cardNo;
    private Date expireDate;
    private String cvc;

    public Card() {
    }

    public Card(int id, String cardNo, Date expireDate, String cvc) {
        this.id = id;
        this.cardNo = cardNo;
        this.expireDate = expireDate;
        this.cvc = cvc;
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

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardNo='" + cardNo + '\'' +
                ", expireDate=" + expireDate +
                ", cvc='" + cvc + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return cardNo.equals(card.cardNo) && expireDate.equals(card.expireDate) && cvc.equals(card.cvc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNo, expireDate, cvc);
    }
}
