package classes;

import javafx.scene.control.Button;

public class SerialsSeries {

    private String seasonNumber;
    private String seriesNumber;
    private Button button;

    public SerialsSeries(String seasonNumber, String seriesNumber) {
        this.seasonNumber = seasonNumber;
        this.seriesNumber = seriesNumber;
        this.button = new Button("X");
    }

    public SerialsSeries() {
    }

    public String getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(String seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getSeriesNumber() {
        return seriesNumber;
    }

    public void setSeriesNumber(String seriesNumber) {
        this.seriesNumber = seriesNumber;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}