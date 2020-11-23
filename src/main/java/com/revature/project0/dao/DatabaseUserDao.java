package com.revature.project0.dao;

import com.revature.project0.models.Card;
import com.revature.project0.models.Role;
import com.revature.project0.models.User;
import com.revature.project0.util.JDBCUtility;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseUserDao {


    public ArrayList<User> getAllUsers() {
        String sqlQuery = "SELECT * "
                + "FROM users u "
                + "INNER JOIN roles r "
                + "ON u.role_id = r.id";


        ArrayList<User> users = new ArrayList<>();

        try(Connection con = JDBCUtility.getConnection()) {

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                int userId = rs.getInt(1);
                String name = rs.getString(2);
                int password = rs.getInt(3);
                String email = rs.getString(5);

                // get cards owned by each user
                ArrayList<Card> cards = new ArrayList<Card>();
                String cardQuery = "SELECT * FROM cards c;";
                ResultSet rsCards = stmt.executeQuery(cardQuery);
                while (rsCards.next()) {
                    int card_id = rsCards.getInt(1);
                    String card_name = rsCards.getString(2);
                    String cardType = rsCards.getString(3);
                    int card_owner = rsCards.getInt(4);

                    if (card_owner == userId) {
                        cards.add(new Card(card_id,card_name,cardType , card_owner));
                    }

                }

                int role_id = rs.getInt(4);
                String role = rs.getString(8);
                Role roleObj = new Role(role_id ,role);
                User user = new User(userId , roleObj, name , email, String.valueOf(password), "10/10/10");
                user.setCards(cards);
                users.add(user);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return users;
    }

    public User getUser(int id) {
//        String sqlQuery = "SELECT * FROM users";
        String sqlQuery = "SELECT * "
                + "FROM users u "
                + "INNER JOIN roles r "
                + "ON u.role_id = r.id";

        try (Connection con = JDBCUtility.getConnection()) {

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                int userId = rs.getInt(1);
                if (userId == id) {

                    String name = rs.getString(2);
                    int password = rs.getInt(3);
                    String email = rs.getString(5);

                    // get cards owned by each user
                    ArrayList<Card> cards = new ArrayList<Card>();
                    String cardQuery = "SELECT * FROM cards c;";
                    ResultSet rsCards = stmt.executeQuery(cardQuery);
                    while (rsCards.next()) {
                        int card_id = rsCards.getInt(1);
                        String card_name = rsCards.getString(2);
                        String cardType = rsCards.getString(3);
                        int card_owner = rsCards.getInt(4);

                        if (card_owner == userId) {
                            cards.add(new Card(card_id,card_name,cardType , card_owner));
                        }

                    }

                    int role_id = rs.getInt(4);
                    String role = rs.getString(8);
                    Role roleObj = new Role(role_id ,role);
                    User user = new User(userId , roleObj, name , email, String.valueOf(password), "10/10/10");
                    user.setCards(cards);
                    return user;
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new User();
    }

    public User insertUser(User newUser) {
        try (Connection connection = JDBCUtility.getConnection()) {
            connection.setAutoCommit(false);

            String sqlQuery = "INSERT INTO users "
                    + "(name,password,email,role_id) "
                    + "VALUES "
                    + "(?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, newUser.getUserName());
            pstmt.setInt(2, Integer.parseInt(newUser.getPassword()));
            pstmt.setString(3, newUser.getEmail());
            pstmt.setInt(4, newUser.getRole_id());

            if (pstmt.executeUpdate() != 1) {
                throw new SQLException("Inserting user failed, no rows were impacted");
            }

            int autoId;
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                autoId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("inserting user failed, there was no id generated");
            }
            connection.commit();
            return new User(autoId, newUser.getRole(), newUser.getUserName(), newUser.getEmail(), newUser.getPassword(), "10/10/1996");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
