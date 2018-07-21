package allMethodsDВ;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static DB.ConnectionDB.connectionDB;

public class GettersSerialMethods {

    public static String DBGetName(int idSerial) {
        String name = "";
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM serials" + " WHERE idSerial = " + "'" + idSerial + "'");
            ResultSet res = stmt.getResultSet();

            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                name = res.getString("name");
            }
            stmt.close();
        } catch (SQLException f) {
            f.printStackTrace();
        }
        return name;
    }

    public static int DBGetCurrentSeason(String name) {
        int currentSeason = 1;
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM serials" + " WHERE name = " + "'" + name + "'");
            ResultSet res = stmt.getResultSet();

            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                if ((res.getString("currentSeason") == null))
                    return currentSeason;
                if (!res.getString("currentSeason").equals(""))
                    currentSeason = Integer.parseInt(res.getString("currentSeason"));


            }
            stmt.close();
        } catch (SQLException f) {
            f.printStackTrace();
        }
        return currentSeason;
    }

    public static int DBGetCurrentSeries(String name) {
        int currentSeries = 1;
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM serials" + " WHERE name = " + "'" + name + "'");
            ResultSet res = stmt.getResultSet();

            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                if ((res.getString("currentSeries") == null))
                    return currentSeries;
                if (!res.getString("currentSeries").equals(""))
                    currentSeries = Integer.parseInt(res.getString("currentSeries"));
            }
            stmt.close();
        } catch (SQLException f) {
            f.printStackTrace();
        }
        return currentSeries;
    }

    public static String DBGetPath(String name) {
        String path = "";
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM serials" + " WHERE name = " + "'" + name + "'");
            ResultSet res = stmt.getResultSet();

            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                path = res.getString("path");
            }
            stmt.close();
        } catch (SQLException f) {
            f.printStackTrace();
        }
        return path;
    }


    public static String DBGetPicture(String name) {
        //TODO
        //тут когда-нить будет гетер картинки
        return "а тут настоящий путь, май братхер";
    }


}
