package classes;

import javafx.scene.control.Button;

public class TimeSeason {
    private String seasonNumber;
    private String seriesNumber;
    private Button button;

    public TimeSeason(String seasonNumber, String seriesNumbed) {
        this.seasonNumber = seasonNumber;
        this.seriesNumber = seriesNumbed;
        this.button = new Button("X");
    }

    public TimeSeason() {
    }

    public String getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(String seasonNumbed) {
        this.seasonNumber = seasonNumbed;
    }

    public String getSeriesNumber() {
        return seriesNumber;
    }

    public void setSeriesNumber(String seriesNumbed) {
        this.seriesNumber = seriesNumbed;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
