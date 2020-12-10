package com.revature.project0.util;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtility {

    public static Connection getConnection() throws SQLException {
//        String url = "jdbc:postgresql://localhost:5432/corysebastian";
//        String username = "postgres";
//        String password = "";
        
        String url = System.getenv("DB_URL");
        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");

        if (url != null && username != null) {
            Connection connection;
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        } else {
            Connection connection;
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        }
    }

}
