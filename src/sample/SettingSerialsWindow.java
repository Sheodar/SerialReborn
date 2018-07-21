package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

class SettingSerialsWindow {

    static void showSettingsWindow(Window mod) throws IOException {
        Stage stg = new Stage();

        Parent root = FXMLLoader.load(SettingSerialsWindow.class.getResource("settingsSerial.fxml"));
        Scene scn = new Scene (root);

        stg.setScene(scn);
        stg.setTitle("Настройки сериала");
        stg.initModality(Modality.WINDOW_MODAL);
        stg.initOwner(mod);
        stg.showAndWait();
    }
}
