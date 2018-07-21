package allMethodsDВ;

import classes.Serial;
import classes.TimeSeason;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static DB.ConnectionDB.connectionDB;
import static allMethodsDВ.UtilsDB.getLastValueIntOfColumn;


public class SerialsMethods {
    public static void addSerial(String name, String path, ObservableList<TimeSeason> seasons) {
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            int idSerial = getLastValueIntOfColumn("serials", "idSerial") + 1;

            stmt.execute("INSERT INTO serials (idSerial, name, path) VALUES " +
                    "('" + idSerial + "','" + name + "','" + path + "')");

            int idSeason;
            for (int x = 0; x < seasons.size(); x++) {
                idSeason = getLastValueIntOfColumn("multTableSeasonAndSerials", "multValue") + 1;
                stmt.execute("INSERT INTO multTableSeasonAndSerials (multKey, multValue) VALUES " +
                        "('" + idSerial + "','" + idSeason + "')");

                String season = seasons.get(x).getSeasonNumber();
                String series = seasons.get(x).getSeriesNumber();

                stmt.execute("INSERT INTO serialSeasonValue (seasonValue, serialValue, seasonNumber) VALUES " +
                        "('" + idSeason + "','" + series + "','" + season + "')");
            }
            stmt.close();
        } catch (SQLException f) {
            f.printStackTrace();
        }
    }

    public static void removeSerial(int idSerial) {
        ArrayList<Integer> multValueMass = new ArrayList<>();
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM serials WHERE idSerial = '" + idSerial + "'");
            ResultSet res = stmt.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                stmt.execute("SELECT * FROM multTableSeasonAndSerials WHERE multKey = '" + idSerial + "'");
                res = stmt.getResultSet();
                while (res.next()) {
                    multValueMass.add(Integer.valueOf(res.getString("multValue")));
                }
                for (Integer multValueMas : multValueMass) {
                    String q = String.valueOf(multValueMas);
                    stmt.execute("DELETE FROM serialSeasonValue WHERE seasonValue = " + "'"+multValueMas+"'");
                }
                for (Integer multValueMas : multValueMass) {
                    String q = String.valueOf(multValueMas);
                    stmt.execute("DELETE FROM multTableSeasonAndSerials WHERE multValue = " + "'"+multValueMas+"'");
                }
                multValueMass.clear();
                stmt.execute("DELETE FROM serials WHERE idSerial = " + "'"+idSerial+"'");

            }

            stmt.close();
        } catch (SQLException f) {
            f.printStackTrace();
        }
    }
}
