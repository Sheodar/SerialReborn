package DB;

import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    public static Connection connectionDB;
    public static void DBConnect2() throws SQLException {
        String url = "jdbc:sqlite:src/DB/serialsDB.db";
        connectionDB = null;
        try {
            connectionDB = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}