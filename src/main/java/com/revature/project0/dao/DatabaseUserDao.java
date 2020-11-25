package com.revature.project0.dao;

import com.revature.project0.models.Card;
import com.revature.project0.models.Role;
import com.revature.project0.models.User;
import com.revature.project0.util.JDBCUtility;

import javax.servlet.http.HttpSession;
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
                String password = rs.getString(3);
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
                String role = rs.getString(7);
                Role roleObj = new Role(role_id ,role);
                User user = new User(userId , roleObj, name , email, password);
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
                    String password = rs.getString(3);
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
                    String role = rs.getString(7);
                    Role roleObj = new Role(role_id ,role);
                    User user = new User(userId , roleObj, name , email, password);
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
            return new User(autoId, newUser.getRole(), newUser.getUserName(), newUser.getEmail(), newUser.getPassword());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public User updateUser(User user , User newFields) {
        try (Connection connection = JDBCUtility.getConnection()) {
            connection.setAutoCommit(false);

            String sqlQuery = "UPDATE users "
                    + "SET name = ?, email = ? "
                    + "WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1, newFields.getUserName());
            pstmt.setString(2, newFields.getEmail());
            pstmt.setInt(3, user.getId());

            if (pstmt.executeUpdate() <= 0) {
                throw new SQLException("updating user failed, no rows were changed");
            }

            connection.commit();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new User();
    }


    public void deleteUser(int id) {
        try (Connection connection = JDBCUtility.getConnection()) {
            connection.setAutoCommit(false);
            String sqlQuery = "DELETE FROM users WHERE id = ?";

            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setInt(1,id);

            if (pstmt.executeUpdate() <= 0) {
                throw new SQLException("failed to delete user. no rows were removed");
            }
            connection.commit();
//            pstmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
