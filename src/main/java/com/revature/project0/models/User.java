package com.revature.project0.models;

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
