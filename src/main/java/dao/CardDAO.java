package dao;

import db.DBCPDataSource;
import db.DBException;
import db.Query;
import models.Account;
import models.Card;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

import static db.Query.CARD_GET_BY_ACCOUNT_ID;

public class CardDAO implements DAO{
    private final Logger logger = Logger.getLogger(CardDAO.class);

    @Override
    public Object get(int id) throws DBException {
        return null;
    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public void save(Object o) throws DBException {
        Connection con = DBCPDataSource.getConnection();
        PreparedStatement preparedStatement = null;
        Card card = (Card) o;
        try {
            preparedStatement = con.prepareStatement(Query.CARD_INSERT, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            preparedStatement.setInt(k++, card.getAccountId());
            preparedStatement.setString(k++, card.getCardNo());
            preparedStatement.setString(k++, card.getCvc());
            preparedStatement.setInt(k++, card.getExpMonth());
            preparedStatement.setInt(k, card.getExpYear());

            int count = preparedStatement.executeUpdate();
            System.out.println(count);
            setGeneratedCardId( card, preparedStatement );
            con.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw  new DBException("Failed to rollback", ex);
            }
            throw  new DBException("Failed to add card", e);
        }
    }
    private void setGeneratedCardId(Card card, PreparedStatement stmt) throws DBException {
        ResultSet generatedKeys = null;
        try {
            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                card.setId(generatedKeys.getInt(1));
            }
            else {
                throw new DBException("Creating account failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new DBException("Creating account failed, no ID obtained.", e);
        }

    }
    @Override
    public void update(Object o, String[] params) {

    }

    @Override
    public void delete(Object o) {

    }

    public void attachCardToAccounts(List<Account> accounts) throws DBException {
        for (Account account: accounts) {
            account.setCard(getByAccountId(account.getId()));
        }
    }
    public Card getByAccountId(int accountId) throws DBException{
        Connection connection = DBCPDataSource.getConnection();
        Card card = null;

        try {
            PreparedStatement statement = connection.prepareStatement(CARD_GET_BY_ACCOUNT_ID);
            statement.setInt(1, accountId);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                card = new Card(rs.getInt("id"), accountId, rs.getString("card_no"),
                        rs.getString("cvc"), rs.getInt("exp_month"), rs.getInt("exp_year"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DBException("Failed to get card for account", e);
        }
        return card;
    }
}
