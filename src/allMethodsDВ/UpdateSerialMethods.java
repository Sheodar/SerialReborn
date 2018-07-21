package allMethodsDВ;

import classes.TimeSeason;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import static DB.ConnectionDB.connectionDB;
import static allMethodsDВ.UtilsDB.getLastValueIntOfColumn;

public class UpdateSerialMethods {

    public static void updateName (String idSerial, String newName){
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM serials");
            ResultSet res = stmt.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                stmt.execute("UPDATE serials SET name = '" + newName + "' WHERE idSerial = " + "'"+idSerial+"'");
            }
            stmt.close();
        } catch (SQLException f) {
            System.out.println("ашибка3");
        }
    }
    public static void updateCurrentSeason (String name, String currentSeason){
        try {
            currentSeason = currentSeason.replaceAll("Сезон:", "").trim();
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM serials");
            ResultSet res = stmt.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                stmt.execute("UPDATE serials SET currentSeason = '" + currentSeason + "' WHERE name = " + "'"+name+"'");
            }
            stmt.close();
        } catch (SQLException f) {
            System.out.println("ашибка4");
        }
    }
    public static void updateCurrentSeries(String name, String currentSeries){
        try {
            currentSeries = currentSeries.replaceAll("Серия:", "").trim();
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM serials");
            ResultSet res = stmt.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                stmt.execute("UPDATE serials SET currentSeries = '" + currentSeries + "' WHERE name = " + "'"+name+"'");
            }
            stmt.close();
        } catch (SQLException f) {
            System.out.println("ашибка5");
        }
    }
    public static void updatePath (String idSerial, String path){
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM serials");
            ResultSet res = stmt.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                stmt.execute("UPDATE serials SET path = '" + path + "' WHERE idSerial = " + "'"+idSerial+"'");
            }
            stmt.close();
        } catch (SQLException f) {
            System.out.println("ашибка6");
        }
    }
    public static void updateSeasons (String idSerial, ObservableList<TimeSeason> seasons){
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
                System.out.println(multValueMass);
                multValueMass.clear();

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

            }
            stmt.close();
        } catch (SQLException f) {
            System.out.println("ашибка3");
        }
    }

    public static void updateComment (String idSerial, String newComment){
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM serials");
            ResultSet res = stmt.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                stmt.execute("UPDATE serials SET comment = '" + newComment + "' WHERE idSerial = " + "'"+idSerial+"'");
            }
            stmt.close();
        } catch (SQLException f) {
            System.out.println("ашибка3");
        }
    }

    public static void updateDiscription (String idSerial, String newDiscription){
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM serials");
            ResultSet res = stmt.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                stmt.execute("UPDATE serials SET discription = '" + newDiscription + "' WHERE idSerial = " + "'"+idSerial+"'");
            }
            stmt.close();
        } catch (SQLException f) {
            System.out.println("ашибка3");
        }
    }

    public static void updatePicture (String idSerial, String pathPicture){
         //TODO
         //тут когда-нить будет апдейт картинки
    }



}
