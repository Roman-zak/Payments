package com.payments.payments.controllers.tags;

import models.Account;
import models.Card;

//import javax.servlet.jsp.tagext.*;
//import javax.servlet.jsp.*;
import jakarta.servlet.jsp.tagext.*;
import jakarta.servlet.jsp.*;
import java.io.IOException;
import java.io.StringWriter;

public class AccountTagController extends SimpleTagSupport{
    private Account account = new Account("0000","UAH",0,new Card("000","355",5,2022),"John", "phoe","addr","pc");
    StringWriter sw = new StringWriter();
    public void doTag()

            throws JspException, IOException {
        if (account != null) {
            /* Use message from attribute */
            JspWriter out = getJspContext().getOut();
            out.println( "Number: "+ account.getAccountNo() );
            out.println( "Balance: "+ account.getBalance() );
            out.println( "Currency: "+ account.getCurrency() );
            out.println( "Owner: "+ account.getOwnerName() );
            out.println( "Owner: "+ account.getOwnerName() );
            out.println( "Owner address: "+ account.getOwnerAddress() );
            out.println( "Postal code: "+ account.getPostalCode() );
            if(account.getCard()!=null){
                out.println( "Card:" );
                out.println( "Number: "+ account.getCard().getCardNo() );
                out.println( "Expire date: "+ account.getCard().getExpMonth()+"/"+account.getCard().getExpYear());
            }
        }
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
