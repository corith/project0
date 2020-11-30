package com.revature.project0.dao;

import com.revature.project0.models.Card;
import com.revature.project0.util.JDBCUtility;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseCardDao {

    /**
     * returns an array list of all the cards in the database. Does not require authentication.
     * @return ArrayList of cards
     */
    public ArrayList<Card> getAllCards() {
        String sqlQuery = "SELECT * FROM cards";
        ArrayList<Card> cards = new ArrayList<>();

        try(Connection connection = JDBCUtility.getConnection()) {

            Statement stmt = connection.createStatement();
            ResultSet rs   = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                int cardId      = rs.getInt(1);
                String cardName = rs.getString(2);
                String cardType = rs.getString(3);
                int cardOwner   = rs.getInt(4);

                cards.add(new Card(cardId , cardName , cardType , cardOwner));
            }
            return cards;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cards;
    }

    /**
     * get a single card by id
     * @param id the id of the card
     * @return Card object if found and blank card if not found
     */
    public Card getCard(int id) {
        String sqlQuery = "SELECT * "
                + "FROM cards c";

        try(Connection connection = JDBCUtility.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs   = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
            int cardId      = rs.getInt(1);
                if (cardId == id) {
                    String cardName = rs.getString(2);
                    String cardType = rs.getString(3);
                    int cardOwner   = rs.getInt(4);
                    return new Card(cardId , cardName , cardType, cardOwner);
                }
            }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new Card();
    }

    /**
     * Create a new card with fields "name", "type", and "owner".
     * @param newCard card to be created
     * @return the card that was inserted or null
     */
    public Card insertCard(Card newCard) {

        try(Connection connection = JDBCUtility.getConnection()) {
            connection.setAutoCommit(false);
            String sqlQuery = "INSERT INTO cards "
                    + "(name,type,owner) "
                    + "VALUES "
                    + "(?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1, newCard.getName());
            pstmt.setString(2, newCard.getType());
            pstmt.setInt(3, newCard.getOwner());

            if(pstmt.executeUpdate() <= 0) {
                throw new SQLException("nah this didn't work dude....no rows were impacted though");
            }

            connection.commit();
            return new Card(newCard.getName() , newCard.getType() , newCard.getOwner());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

    /**
     * Deletes a card with the id of id
     * @param id the id of the card that should be deleted
     */
    public void deleteCard(int id) {
        try (Connection connection = JDBCUtility.getConnection()) {
            connection.setAutoCommit(false);
            String sqlQuery = "delete from cards where id=?";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setInt(1,id);

            if (pstmt.executeUpdate() <= 0) {
                throw new SQLException("failed to delete card...no cards were removed");
            }
            connection.commit();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Updates a card. Can only update a cards "name" and "type".
     * @param cardToBeUpdated the card to be updated
     * @param newFields Card object that only has name and type fields
     */
    public void updateCard(Card cardToBeUpdated, Card newFields) {
        try (Connection connection = JDBCUtility.getConnection()) {
            connection.setAutoCommit(false);
            String sqlQuery = "update cards set name = ?, type = ? where id=?";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1, newFields.getName());
            pstmt.setString(2, newFields.getType());
            pstmt.setInt(3,cardToBeUpdated.getId());
            if (pstmt.executeUpdate() <= 0) {
                throw new SQLException("updating user faild, no rows were updated..");
            }
            connection.commit();
            pstmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
