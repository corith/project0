package com.revature.project0.dao;

import com.revature.project0.models.User;
import com.revature.project0.util.JDBCUtility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int password = rs.getInt(3);
                int role_id = rs.getInt(4);
                String email = rs.getString(5);

                User user = new User(id ,name , email, String.valueOf(password), "10/10/10");

                users.add(user);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return users;
    }
}
