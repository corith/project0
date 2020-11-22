package com.revature.project0;

import com.revature.project0.models.User;
import com.revature.project0.util.JDBCUtility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException {
        User user = new User("test" , "jfk" , "459" , "test");
        System.out.println( user );

        String sqlQ = "SELECT * FROM users u";
        try(Connection con = JDBCUtility.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQ);
            rs.next();
            System.out.println(rs.getString(2));
        }
    }
}
