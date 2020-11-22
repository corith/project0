package com.revature.project0.models;

public class Role {
    private int id;
    private String role;

    public Role() {
        super();
    }

    public Role(int id, String role) {
        this.id = id;
        this.role = role;
    }

    // getters
    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Role: " + this.role + " id: " + this.id;
    }
}
