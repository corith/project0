package com.revature.project0.dao;

import com.revature.project0.models.Card;
import com.revature.project0.util.JDBCUtility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
