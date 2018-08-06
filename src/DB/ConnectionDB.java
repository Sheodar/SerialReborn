package DB;

import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    public static Connection connectionDB;

    public static void DBConnect() {
        String url = "jdbc:sqlite:serialsDB.db";
        connectionDB = null;
        try {
            connectionDB = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}