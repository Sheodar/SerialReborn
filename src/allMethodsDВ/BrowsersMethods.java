package allMethodsDВ;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static DB.ConnectionDB.connectionDB;
import static allMethodsDВ.UtilsDB.getLastValueIntOfColumn;


public class BrowsersMethods {
    /**************************************************************************************************************/

    public static void addBrowser(String name, String path) {
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            int idBrowsers = getLastValueIntOfColumn("browsers", "idBrowsers") + 1;

            stmt.execute("INSERT INTO browsers (idBrowsers, name, path, currentBrowser) VALUES " +
                    "('" + idBrowsers + "','" + name + "','" + path + "','" + "0" + "')");
            stmt.close();
        } catch (SQLException f) {
            f.printStackTrace();
        }
    }

    /**************************************************************************************************************/

    public static void removeBrowser(int idBrowsers) {
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM browsers WHERE idBrowsers = '" + idBrowsers + "'");
            ResultSet res = stmt.getResultSet();
            if (!res.next()) {
                System.out.println("Not created browsers. Please, create signature.");
                res.close();
            } else {
                stmt.execute("DELETE FROM browsers WHERE idBrowsers = " + "'" + idBrowsers + "'");

            }
            stmt.close();
        } catch (SQLException f) {
            f.printStackTrace();
        }
    }

    /**************************************************************************************************************/
}
