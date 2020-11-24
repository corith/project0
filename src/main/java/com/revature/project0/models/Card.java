package com.revature.project0.models;

public class Card {
    private int id;
    private String name;
    private String type;
    private int owner;

    public Card() {
        super();
        this.name = "blank";
    }

    public Card(int id, String name, String type, int owner) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.owner = owner;
    }

    public Card(String name, String type, int owner) {
        this.name = name;
        this.type = type;
        this.owner = owner;
    }

    // getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getOwner() {
        return owner;
    }



    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
}
