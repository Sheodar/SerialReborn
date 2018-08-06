package allMethodsDВ;

import classes.Serial;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static DB.ConnectionDB.connectionDB;

public class GettersSerialMethods {
    /**************************************************************************************************************/

    public static ArrayList<Serial> getTableSerialsNameForDB() {
        ArrayList<Serial> resultSerials = new ArrayList<>();
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM serials");
            ResultSet res = stmt.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                stmt.execute("SELECT * FROM serials");
                res = stmt.getResultSet();

                while (res.next()) {
                    String idSerial = res.getString("idSerial");
                    String name = res.getString("name");
                    String path = res.getString("path");
                    String currentSeason = res.getString("currentSeason");
                    String currentSeries = res.getString("currentSeries");
                    String comment = res.getString("comment");
                    String discription = res.getString("discription");

                    resultSerials.add(new Serial(idSerial, name, path, getSerialsForSerial(name), currentSeason, currentSeries, comment, discription));
                }
                res.close();
            }
            stmt.close();
        } catch (SQLException f) {
            f.printStackTrace();
        }

        return resultSerials;
    }

    /**************************************************************************************************************/

    private static Map<Integer, Integer> getSerialsForSerial(String name) throws SQLException {
        Map<Integer, Integer> seriesValue = new HashMap<>();
        ArrayList<Integer> multValueMass = new ArrayList<>();

        try {

            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM serials");
            ResultSet res = stmt.getResultSet();

            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {

                stmt.execute("SELECT * FROM serials WHERE name = '" + name + "'");
                res = stmt.getResultSet();
                String z = res.getString("idSerial");
                stmt.execute("SELECT * FROM multTableSeasonAndSerials WHERE multKey = '" + z + "'");
                res = stmt.getResultSet();
                while (res.next()) {
                    multValueMass.add(Integer.valueOf(res.getString("multValue")));
                }
                for (Integer multValueMas : multValueMass) {
                    String q = String.valueOf(multValueMas);
                    stmt.execute("SELECT * FROM serialSeasonValue WHERE seasonValue = '" + q + "'");
                    res = stmt.getResultSet();
                    seriesValue.put(Integer.valueOf(res.getString("seasonNumber")), Integer.valueOf(res.getString("serialValue")));
                    stmt.execute("SELECT * FROM multTableSeasonAndSerials WHERE multKey = '" + z + "'");
                }
            }
            stmt.close();
        } catch (SQLException f) {
            f.printStackTrace();
        }
        return seriesValue;
    }

    /**************************************************************************************************************/

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

    /**************************************************************************************************************/

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

    /**************************************************************************************************************/

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

    /**************************************************************************************************************/

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

    /**************************************************************************************************************/

    public static String DBGetPicture(String name) {
        //TODO
        //тут когда-нить будет гетер картинки
        return "а тут настоящий путь, май братхер";
    }

    /**************************************************************************************************************/
}
