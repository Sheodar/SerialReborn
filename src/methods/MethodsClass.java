package methods;

import classes.Serial;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static DB.ConnectionDB.*;

public class MethodsClass {

    public static ArrayList<Serial> getTableSerialsNameForDB() throws SQLException {
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
                ResultSet res2 = stmt.getResultSet();

                while (res2.next()) {
                    String idSerial = res2.getString("idSerial");
                    String name = res2.getString("name");
                    String path = res2.getString("path");
                    String currentSeason = res2.getString("currentSeason");
                    String currentSeries = res2.getString("currentSeries");
                    String comment = res2.getString("comment");
                    String discription = res2.getString("discription");

                    resultSerials.add(new Serial(idSerial, name, path, getSerialsForSerial(name), currentSeason, currentSeries, comment, discription));
                }
                res2.close();
            }
            stmt.close();
        } catch (SQLException f) {
            System.out.println("ашибка1");
        }

        return resultSerials;
    }

    public static Map<Integer, Integer> getSerialsForSerial(String name) throws SQLException {
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
//                    seriesValue.put(Integer.valueOf(res.getString("serialValue")), Integer.valueOf(res.getString("seasonNumber")));
                    seriesValue.put(Integer.valueOf(res.getString("seasonNumber")), Integer.valueOf(res.getString("serialValue")));
                    stmt.execute("SELECT * FROM multTableSeasonAndSerials WHERE multKey = '" + z + "'");
                }
            }
            stmt.close();
        } catch (SQLException f) {
            System.out.println("ашибка2");
        }
        return seriesValue;
    }
}
