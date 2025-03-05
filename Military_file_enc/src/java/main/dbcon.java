package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author 
 */
public class dbcon {

    public static Connection conn = null;
    public static Statement st = null;
    PreparedStatement pst = null;
    static String url = "jdbc:mysql://localhost:3306/";
    static String dbName = "enc";
    static String driver = "com.mysql.jdbc.Driver";
    static String dbUserName = "root";
    static String dbPassword = "";

    public static Statement connectDB() throws ClassNotFoundException, SQLException {
        if (conn == null) {
            Class.forName(driver);
            conn = DriverManager.getConnection(url + dbName, dbUserName, dbPassword);
            st = conn.createStatement();
        }
        return st;
    }

    public static Connection connectDB_con() throws ClassNotFoundException, SQLException {
        if (conn == null) {
            Class.forName(driver);
            conn = DriverManager.getConnection(url + dbName, dbUserName, dbPassword);
            // st = conn.createStatement();
        }
        return conn;
    }
}
