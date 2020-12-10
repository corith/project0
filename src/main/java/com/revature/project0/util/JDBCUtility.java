package com.revature.project0.util;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtility {

    public static Connection getConnection() throws SQLException {
//        String url = "jdbc:postgresql://localhost:5432/corysebastian";
        String username = "postgres";
        String password = "";
	String url = "jdbc:postgresql://project0database.cz0mwufyd0pp.us-east-2.rds.amazonaws.com:5432/p0database";
	String un  = "corith";
	String pw  = "project044";

        Connection connection;
        DriverManager.registerDriver(new Driver());
        //connection = DriverManager.getConnection(url, username, password);
        connection = DriverManager.getConnection(url, un, pw);
        return connection;
    }

}
