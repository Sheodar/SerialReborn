package classes;

import javafx.scene.control.Button;

public class Browser {

    private String name;
    private String path;
    private int idBrowserDB;
    private Button getPathButton;
    private Button removeButton;
    private String picturePath;

    public Browser(int idSerialDB, String name, String path, String picturePath) {
        this.name = name;
        this.path = path;
        this.idBrowserDB = idSerialDB;
        this.getPathButton = new Button("Указать путь");
        this.removeButton = new Button("X");
        this.picturePath = picturePath;

    }

    public Browser() {
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

    public void setPath(String path) { this.path = path; }

    public int getIdBrowserDB() { return idBrowserDB; }

    public void setIdBrowserDB(int idSerialDB) { this.idBrowserDB = idSerialDB; }

    public Button getGetPathButton() {
        return getPathButton;
    }

    public void setGetPathButton(Button getPathButton) {
        this.getPathButton = getPathButton;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public Button getRemoveButton() {
        return removeButton;
    }
    public void setRemoveButton(Button removeButton) {
        this.removeButton = removeButton;
    }
}