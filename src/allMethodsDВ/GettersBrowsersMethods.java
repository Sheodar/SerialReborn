package allMethodsDВ;

import classes.Browser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static DB.ConnectionDB.connectionDB;

public class GettersBrowsersMethods {
    /**************************************************************************************************************/

    public static ArrayList<Browser> getTableBrowsersNameForDB() {
        ArrayList<Browser> resultBrowsers = new ArrayList<>();
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM browsers");
            ResultSet res = stmt.getResultSet();
            if (!res.next()) {
                System.out.println("You don't have browsers. Please, create signature.");
                res.close();
            } else {
                stmt.execute("SELECT * FROM browsers");
                res = stmt.getResultSet();

                while (res.next()) {
                    String idBrowsers = res.getString("idBrowsers");
                    String name = res.getString("name");
                    String path = res.getString("path");
                    String picturePath = res.getString("picturePath");

                    resultBrowsers.add(new Browser(Integer.parseInt(idBrowsers), name, path, picturePath));
                }
                res.close();
            }
            stmt.close();
        } catch (SQLException f) {
            System.out.println("Не может вытянуть браузеры");
        }

        return resultBrowsers;
    }

    /**************************************************************************************************************/

    public static int DBGetCurrentBrowserID() {
        int currentSeason = 1;
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM browsers");
            ResultSet res = stmt.getResultSet();

            if (!res.next()) {
                System.out.println("Not created browser. Please, create signature_her.");
                res.close();
            } else {
                stmt.execute("SELECT * FROM browsers" + " WHERE currentBrowser = 1");
                res = stmt.getResultSet();
                if (!res.next()) {
                    return currentSeason;
                } else {
                    currentSeason = Integer.parseInt(res.getString("idBrowsers"));
                }
            }
            stmt.close();
        } catch (SQLException f) {
            f.printStackTrace();
        }
        return currentSeason;
    }

    /**************************************************************************************************************/

    public static String DBGetCurrentBrowserPath() {
        String path = "";
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM browsers");
            ResultSet res = stmt.getResultSet();

            if (!res.next()) {
                System.out.println("Not created browser. Please, create signature_her.");
                res.close();
            } else {
                stmt.execute("SELECT * FROM browsers" + " WHERE currentBrowser = 1");
                res = stmt.getResultSet();
                if (!res.next()) {
                    System.out.println("Not created browser. Please, create signature_her.");
                    res.close();
                } else {
                    path = res.getString("path");
                }
            }
            stmt.close();
        } catch (SQLException f) {
            f.printStackTrace();
        }
        return path;
    }

    /**************************************************************************************************************/
}
