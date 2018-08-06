package allMethodsDВ;

import classes.SerialsSeries;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static DB.ConnectionDB.connectionDB;
import static allMethodsDВ.UtilsDB.getLastValueIntOfColumn;

public class UpdateSerialMethods {
    /**************************************************************************************************************/

    public static void updateName(String idSerial, String newName) {
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM serials");
            ResultSet res = stmt.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                stmt.execute("UPDATE serials SET name = '" + newName + "' WHERE idSerial = " + "'" + idSerial + "'");
            }
            stmt.close();
        } catch (SQLException f) {
            f.printStackTrace();
        }
    }

    /**************************************************************************************************************/

    public static void updateCurrentSeason(String name, String currentSeason) {
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
                stmt.execute("UPDATE serials SET currentSeason = '" + currentSeason + "' WHERE name = " + "'" + name + "'");
            }
            stmt.close();
        } catch (SQLException f) {
            f.printStackTrace();
        }
    }

    /**************************************************************************************************************/

    public static void updateCurrentSeries(String name, String currentSeries) {
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
                stmt.execute("UPDATE serials SET currentSeries = '" + currentSeries + "' WHERE name = " + "'" + name + "'");
            }
            stmt.close();
        } catch (SQLException f) {
            f.printStackTrace();
        }
    }

    /**************************************************************************************************************/

    public static void updatePath(String idSerial, String path) {
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM serials");
            ResultSet res = stmt.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                stmt.execute("UPDATE serials SET path = '" + path + "' WHERE idSerial = " + "'" + idSerial + "'");
            }
            stmt.close();
        } catch (SQLException f) {
            f.printStackTrace();
        }
    }

    /**************************************************************************************************************/

    public static void updateSeasons(String idSerial, ObservableList<SerialsSeries> seasons) {
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
                    stmt.execute("DELETE FROM serialSeasonValue WHERE seasonValue = " + "'" + multValueMas + "'");
                }
                for (Integer multValueMas : multValueMass) {
                    stmt.execute("DELETE FROM multTableSeasonAndSerials WHERE multValue = " + "'" + multValueMas + "'");
                }
                multValueMass.clear();

                int idSeason;
                for (int x = 0; x < seasons.size(); x++) {
                    idSeason = getLastValueIntOfColumn("multTableSeasonAndSerials", "multValue") + 1;
                    stmt.execute("INSERT INTO multTableSeasonAndSerials (multKey, multValue) VALUES " +
                            "('" + idSerial + "','" + idSeason + "')");

                    String season = String.valueOf(seasons.get(x).getSeasonNumber());
                    String series = String.valueOf(seasons.get(x).getSeriesNumber());

                    stmt.execute("INSERT INTO serialSeasonValue (seasonValue, serialValue, seasonNumber) VALUES " +
                            "('" + idSeason + "','" + series + "','" + season + "')");
                }

            }
            stmt.close();
        } catch (SQLException f) {
            f.printStackTrace();
        }
    }

    /**************************************************************************************************************/

    public static void updateComment(String idSerial, String newComment) {
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM serials");
            ResultSet res = stmt.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                stmt.execute("UPDATE serials SET comment = '" + newComment + "' WHERE idSerial = " + "'" + idSerial + "'");
            }
            stmt.close();
        } catch (SQLException f) {
            f.printStackTrace();
        }
    }

    /**************************************************************************************************************/

    public static void updateDiscription(String idSerial, String newDiscription) {
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM serials");
            ResultSet res = stmt.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                stmt.execute("UPDATE serials SET discription = '" + newDiscription + "' WHERE idSerial = " + "'" + idSerial + "'");
            }
            stmt.close();
        } catch (SQLException f) {
            f.printStackTrace();
        }
    }

    /**************************************************************************************************************/

    public static void updatePicture(String idSerial, String pathPicture) {
        //TODO
        //тут когда-нить будет апдейт картинки
    }

    /**************************************************************************************************************/
}
