package com.revature.project0.dao;

import com.revature.project0.models.Card;
import com.revature.project0.util.JDBCUtility;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseCardDao {
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

    public Card getCard(int id) {
        String sqlQuery = "SELECT * "
                + "FROM cards c";

        try(Connection connection = JDBCUtility.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs   = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
//            rs.next();
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
}
