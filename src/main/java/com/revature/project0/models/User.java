package com.revature.project0.models;

import com.revature.project0.util.JDBCUtility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {

    private int id;
    private String userName;
    private String email;
    private String password;
    private Role role;
    private int role_id;
    private ArrayList<Card> cards;
    private boolean isLoggedIn;

    public User() {
        this.userName = "blank";
        this.password = "1234";
    }

    public User(String userName , String password) {
        this.userName = userName;
        this.password = password;
        this.isLoggedIn = true;
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

    public boolean isLoggedIn() {
        return isLoggedIn;
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


    public boolean login(String userName , String password) throws SQLException {
        String sqlQ = "SELECT * FROM users";
        try(Connection con = JDBCUtility.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQ);
            while (rs.next()) {
                if (this.userName.equals(rs.getString(2)) && this.password.equals(String.valueOf(rs.getInt(3)))) {
                    this.isLoggedIn = true;
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public String toString() {
        return this.userName + " " + this.password + " " + this.email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        User user = (User) obj;
        return this.getUserName().equals(user.getUserName()) && this.getPassword().equals(user.getPassword());
    }
}
