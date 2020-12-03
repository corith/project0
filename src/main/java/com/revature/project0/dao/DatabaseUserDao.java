package com.revature.project0.dao;

import com.revature.project0.models.Card;
import com.revature.project0.models.Role;
import com.revature.project0.models.User;
import com.revature.project0.util.JDBCUtility;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseUserDao {

    /**
     * Gets all the users in the database - must be logged in with an admin account (role == 1)
     * NOTE: does not return the users in order of id
     * @return ArrayList of User objects (returns empty list if no users are found)
     */
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
                String email = rs.getString(6);

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

                int role_id = rs.getInt(5);
                String role = rs.getString(8);
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

    /**
     * finds a user by it's id
     * @param id the id of the user you are trying to find
     * @return User - returns a "blank" user if none are found
     */
    public User getUser(int id) {
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
                    String email = rs.getString(6);

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

                    int role_id = rs.getInt(5);
                    String role = rs.getString(8);
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

    /**
     * Creates a new User - must have all required fields in the body of the request
     * userName, email, password, role_id
     * @param newUser The User you want to add
     * @param salt The salt used to hash password
     * @param hashedPassword The hashed password given by insertUser() in UserService.java
     * @return User object if created successfully and null if not
     */
    public User insertUser(User newUser, String salt , String hashedPassword) {
        try (Connection connection = JDBCUtility.getConnection()) {
            connection.setAutoCommit(false);

            String sqlQuery = "INSERT INTO users "
                    + "(name,password,salt,email,role_id) "
                    + "VALUES "
                    + "(?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, newUser.getUserName());
            pstmt.setString(2, hashedPassword);
            pstmt.setString(3 , salt);
            pstmt.setString(4, newUser.getEmail());
            pstmt.setInt(5, newUser.getRole_id());

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

    /**
     * Updates a user with new fields...currently can only change a users name and email
     * @param user User that if to be changed
     * @param newFields User object that contains only a userName and an Email
     * @return User (this needs changed)
     */
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


    /**
     * deletes a user with the id of id
     * @param id the id of the user you want to delete
     */
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
            pstmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
