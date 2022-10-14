package services;

import dao.CardDAO;
import dao.UserDAO;
import db.DBException;
import models.Card;

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
