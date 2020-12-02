package com.revature.project0.util;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtility {

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/corysebastian";
        String username = "postgres";
        String password = "";

        Connection connection;
        DriverManager.registerDriver(new Driver());
        connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

}
