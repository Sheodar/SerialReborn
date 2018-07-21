package classes;

import javafx.scene.control.Button;

public class SerialsSeries {

    private int seasonNumbed;
    private int seriesNumbed;
    private Button button;

    public SerialsSeries(int seasonNumbed, int seriesNumbed) {
        this.seasonNumbed = seasonNumbed;
        this.seriesNumbed = seriesNumbed;
        this.button = new Button("X");
    }

    public SerialsSeries() {
    }

    public int getSeasonNumbed() {
        return seasonNumbed;
    }

    public void setSeasonNumbed(int seasonNumbed) {
        this.seasonNumbed = seasonNumbed;
    }

    public int getSeriesNumbed() {
        return seriesNumbed;
    }

    public void setSeriesNumbed(int seriesNumbed) {
        this.seriesNumbed = seriesNumbed;
    }
    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}