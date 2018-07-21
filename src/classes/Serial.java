package classes;

import java.util.Map;

public class Serial {

    private String name;
    private String path;
    private Map series;
    private String currentSeason;
    private String currentSerial;
    private String idSerialDB;
    private String comment;
    private String discription;


    public Serial(String idSerialDB, String name, String path, Map<Integer, Integer> series, String currentSeason, String currentSerial, String comment, String discription) {
        this.name = name;
        this.path = path;
        this.series = series;
        this.currentSeason = currentSeason;
        this.currentSerial = currentSerial;
        this.idSerialDB = idSerialDB;
        this.comment = comment;
        this.discription = discription;

    }

    public Serial() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map getSeries() {
        return series;
    }

    public void setSeries(Map series) {
        this.series = series;
    }

    public String getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(String currentSeason) {
        this.currentSeason = currentSeason;
    }

    public String getCurrentSerial() {
        return currentSerial;
    }

    public void setCurrentSerial(String currentSerial) {
        this.currentSerial = currentSerial;
    }

    public String getIdSerialDB() {
        return idSerialDB;
    }

    public void setIdSerialDB(String idSerialDB) {
        this.idSerialDB = idSerialDB;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
}