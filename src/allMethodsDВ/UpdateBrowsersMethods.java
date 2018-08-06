package allMethodsDВ;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static DB.ConnectionDB.connectionDB;

public class UpdateBrowsersMethods {
    /**************************************************************************************************************/

    public static void updateCurrentBrowser(String name) {
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM browsers");
            ResultSet res = stmt.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                stmt.execute("UPDATE browsers SET currentBrowser = 1 WHERE name = " + "'" + name + "'");
                stmt.execute("UPDATE browsers SET currentBrowser = 0 WHERE name <> " + "'" + name + "'");
            }
            stmt.close();
        } catch (SQLException f) {
            f.printStackTrace();
        }
    }

    /**************************************************************************************************************/

    public static void updatePicturePath(String name, String picturePath) {
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM browsers");
            ResultSet res = stmt.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                stmt.execute("UPDATE browsers SET picturePath = '" + picturePath + "' WHERE name = " + "'" + name + "'");
            }
            stmt.close();
        } catch (SQLException f) {
            f.printStackTrace();
        }
    }

    /**************************************************************************************************************/

    public static void updatePath(String name, String path) {
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM browsers");
            ResultSet res = stmt.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                stmt.execute("UPDATE browsers SET path = '" + path + "' WHERE name = " + "'" + name + "'");
            }
            stmt.close();
        } catch (SQLException f) {
            f.printStackTrace();
        }
    }

    /**************************************************************************************************************/

    public static void updateName(String idBrowser, String name) {
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM browsers");
            ResultSet res = stmt.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                stmt.execute("UPDATE browsers SET name = '" + name + "' WHERE idBrowsers = " + "'" + idBrowser + "'");
            }
            stmt.close();
        } catch (SQLException f) {
            f.printStackTrace();
        }
    }

    /**************************************************************************************************************/
}
