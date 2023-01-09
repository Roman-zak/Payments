package com.payments.dao;

import com.payments.data.DBCPDataSource;
import com.payments.data.DBException;
import com.payments.data.Query;
import com.payments.models.Account;
import com.payments.models.Card;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.payments.data.Query.CARD_GET_BY_ACCOUNT_ID;
public class CardDAO implements DAO<Card>{
    private final Logger logger = Logger.getLogger(CardDAO.class);

    @Override
    public Card get(int id) throws DBException {
        Card card = new Card();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = DBCPDataSource.getConnection();
            preparedStatement = con.prepareStatement(Query.CARD_GET_BY_ID);
            preparedStatement.setInt(1,id);
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                int k;
                if (resultSet.next()) {
                    k=1;
                    card.setId(resultSet.getInt(k++));
                    card.setAccountId(resultSet.getInt(k++));
                    card.setCardNo(resultSet.getString(k++));
                    card.setCvc(resultSet.getString(k++));
                    card.setExpMonth(resultSet.getInt(k++));
                    card.setExpYear(resultSet.getInt(k));
                }
            }
            con.commit();
            return card;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new DBException("Can't get card from database", e);
            }
            throw new DBException("Can't get card from database", e);
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
                con.close();
            } catch (SQLException e){
                throw new DBException("Can't get card from database", e);
            }
        }
    }
    @Override
    public List<Card> getAll() throws DBException {
        List<Card> cards = new ArrayList<>();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = DBCPDataSource.getConnection();
            preparedStatement = con.prepareStatement(Query.CARD_GET_ALL);
            if (preparedStatement.execute()){
                resultSet = preparedStatement.getResultSet();
                int k;
                Card card = null;
                while (resultSet.next()) {
                    card = new Card();
                    k=1;
                    card.setId(resultSet.getInt(k++));
                    card.setAccountId(resultSet.getInt(k++));
                    card.setCardNo(resultSet.getString(k++));
                    card.setCvc(resultSet.getString(k++));
                    card.setExpMonth(resultSet.getInt(k++));
                    card.setExpYear(resultSet.getInt(k));
                    cards.add(card);
                }
            }
            con.commit();
            return cards;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new DBException("Can't get cards from database", e);
            }
            throw new DBException("Can't get cards from database", e);
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
                con.close();
            } catch (SQLException e){
                throw new DBException("Can't get cards from database", e);
            }
        }
    }
    @Override
    public void save(Card o) throws DBException {
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

        } catch (SQLException e) {
            logger.warn(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw  new DBException("Failed to rollback", ex);
            }
            throw  new DBException("Failed to add card", e);
        } finally {
            try {
                preparedStatement.close();
                con.close();
            } catch (SQLException e){
                throw new DBException("Can't get Users from database", e);
            }
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
    public void update(Card card) throws DBException {
        Connection con = DBCPDataSource.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(Query.CARD_UPDATE);
            int k = 1;
            preparedStatement.setInt(k++, card.getAccountId());
            preparedStatement.setString(k++, card.getCardNo());
            preparedStatement.setString(k++, card.getCvc());
            preparedStatement.setInt(k++, card.getExpMonth());
            preparedStatement.setInt(k++, card.getExpYear());
            preparedStatement.setInt(k, card.getId());
            preparedStatement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
                throw new DBException("Can not rollback");
            }
            throw new DBException("Can not update card");
        } finally {
            try {
                con.close();
                preparedStatement.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new DBException("Can not close resources");
            }
        }
    }
    @Override
    public void delete(Card card) throws DBException {
        Connection con = DBCPDataSource.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(Query.CARD_DELETE);
            int k = 1;
            preparedStatement.setInt(k, card.getId());
            preparedStatement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
                throw new DBException("Can not rollback");
            }
            throw new DBException("Can not delete card");
        } finally {
            try {
                con.close();
                preparedStatement.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new DBException("Can not close resources");
            }
        }
    }
    public void attachCardToAccounts(List<Account> accounts) throws DBException {
        for (Account account: accounts) {
            account.setCard(getByAccountId(account.getId()));
        }
    }
    public Card getByAccountId(int accountId) throws DBException{
        Connection connection = DBCPDataSource.getConnection();
        Card card = null;
        ResultSet rs = null;
        PreparedStatement statement = null;
        try {
             statement = connection.prepareStatement(CARD_GET_BY_ACCOUNT_ID);
            statement.setInt(1, accountId);

            rs = statement.executeQuery();
            if (rs.next()) {
                card = new Card(rs.getInt("id"), rs.getInt("account_id"), rs.getString("card_no"),
                        rs.getString("cvc"), rs.getInt("exp_month"), rs.getInt("exp_year"));
            } else {
                throw new DBException("Failed to get card for account");
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DBException("Failed to get card for account", ex);
            }
            throw new DBException("Failed to get card for account", e);
        } finally {
            try {
                rs.close();
                statement.close();
                connection.close();
            } catch (SQLException e){
                throw new DBException("Can't get Users from database", e);
            }
        }
        return card;
    }

    public void save(Card o, Connection con)throws DBException {
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
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            throw  new DBException("Failed to add card", e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e){
                throw new DBException("Failed to close prepare statement", e);
            }
        }
    }

    public void delete(int accountId, Connection con) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(Query.CARD_DELETE_BY_ACCOUNT_ID);
            int k = 1;
            preparedStatement.setInt(k, accountId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new SQLException("can not delete credit card", e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new SQLException("Can not close resources",e);
            }
        }
    }
}
