package com.revature.project0;

import com.github.javafaker.Faker;

import com.revature.project0.models.Card;
import com.revature.project0.models.Role;
import com.revature.project0.models.User;
import com.revature.project0.service.CardService;
import com.revature.project0.service.UserService;
import com.revature.project0.util.JDBCUtility;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Seeder class
 * Will drop all tables if they exist - create them again and then make users and cards for those users.
 * Currently, just like everything, it is a WIP.
 * All user's passwords are "secret"
 * */
public class Seeder {

    public static void main(String[] args) {
        createAllTables();

        UserService us = new UserService();
        CardService cs = new CardService();
        Faker faker = new Faker();

        // insert an admin user
        User admin = new User(1 , new Role(1 , "admin") , "King Admin" , "admin@gmail.com" , "secret");
        admin.setRole_id(1);
        us.insertUser(admin);

        // insert 10 users with a role of "user" (role_id = 2)
        // note that as of now the role id is set on line 43 and not in the creation
        // of the Role object. That will be fixed eventually...
        for (int i = 0; i < 10; i++) {
            String name     = faker.name().fullName();
            String email    = (name + "@yahoo.com").replaceAll("\\s+", "");
            String password = "secret";
            Role role       = new Role(2 , "user");
            User user       = new User(i , role, name, email, password);
            user.setRole_id(2);
            us.insertUser(user);
        }

        // make cards for users and assigns them randomly
        for (int i = 0; i < 23; i++) {
            String cardName = "Card" + i;
            String type     = faker.harryPotter().spell();
            int ownerId     = (int) (Math.random() * (9 - 1 + 1)) + 1;
            Card card = new Card(i , cardName , type , ownerId);
            cs.insertCard(card);
        }
    }

    private static void createAllTables() {
        dropTables();
        createRolesTable();
        addRoleIds();
        createUserTable();
        createCardsTable();
    }

    private static void dropTables() {
        try (Connection con = JDBCUtility.getConnection()) {
            con.setAutoCommit(false);
            String dropTableQuery = "drop table if exists users, roles, cards cascade";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(dropTableQuery);
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void createRolesTable() {
        try (Connection con = JDBCUtility.getConnection()) {
            con.setAutoCommit(false);
            String createTablesQuery = "CREATE TABLE roles " +
                    "(id SERIAL primary key, " +
                    "role VARCHAR(255) not null);";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(createTablesQuery);
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void createUserTable() {
        try (Connection con = JDBCUtility.getConnection()) {
            con.setAutoCommit(false);
            String createTablesQuery = "CREATE TABLE users " +
                    "(id SERIAL primary key, " +
                    "name VARCHAR(255) not null, " +
                    "password VARCHAR(255) not null, " +
                    "salt VARCHAR(255) not null, " +
                    "role_id INTEGER REFERENCES roles(id) on delete cascade, " +
                    "email VARCHAR(255))";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(createTablesQuery);
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void createCardsTable() {
        try (Connection con = JDBCUtility.getConnection()) {
            con.setAutoCommit(false);
            String createTablesQuery = "CREATE TABLE cards " +
                    "(id SERIAL primary key, " +
                    "name varchar(255) not null, " +
                    "type VARCHAR(255) not null, " +
                    "owner integer references users(id) on delete cascade)";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(createTablesQuery);
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void addRoleIds() {
        try (Connection con = JDBCUtility.getConnection()) {
            con.setAutoCommit(false);
            String addRoleIds = "insert into roles (role) values ('admin'), ('user') , ('rogue')";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(addRoleIds);
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
