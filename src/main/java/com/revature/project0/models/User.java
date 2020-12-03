package com.revature.project0.models;

import com.revature.project0.service.UserService;
import com.revature.project0.util.JDBCUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {

    private int id;
    private String userName;
    private String email;
    private String password;
    private Role role;
    private int role_id;
    private ArrayList<Card> cards;

    public User() {
        this.userName = "blank";
        this.password = "1234";
    }

    public User(String userName , String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User(int id , Role role,  String userName, String email, String password) {
        this.id = id;
        this.role = role;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int getRole_id() {
        return role_id;
    }

    // setters
    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
    public void setRole_id(int id) {
        this.role_id = id;
    }

    /**
     * Returns the users salt from the DB
     * @return String for the user's salt
     */
    public String getSalt() {
        UserService us = new UserService();
        String sql = "select salt from users where name = ?";
        try {
            Connection con = JDBCUtility.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1 , this.getUserName());
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getString("salt");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the users hashed password from the DB
     * @return String for the user's hashed password - will return null if SQLException is thrown
     */
    public String getHashedPass() {
        String sql = "select password from users where name = ?";
        try {
            Connection con = JDBCUtility.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, this.getUserName());
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getString("password");
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return this.userName + " " + this.password + " " + this.email;
    }

    /**
     * Deprecated - will most likely be removed in future..
     * @param obj object to be compared
     * @return true if equal and false if not
     */
    @Override
    public boolean equals(Object obj) {
        UserService us = new UserService();
        String salt = this.getSalt();

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        User user = (User) obj;
        String hashed = us.hashingMethod(this.getPassword() , salt);
        return this.getUserName().equals(user.getUserName()) && hashed.equals(us.hashingMethod(user.getPassword() , user.getSalt()));
    }
}
