package com.payments.services;

import com.payments.dao.CardDAO;
import com.payments.data.DBException;
import com.payments.models.Card;

public class CardService {
    private static CardService instance;
    private final CardDAO cardDAO = new CardDAO();

    public static synchronized CardService getInstance() {

        if (instance == null) {
            instance = new CardService();
        }
        return instance;
    }
    public void save(Card card) throws DBException {
        cardDAO.save(card);
    }
}
