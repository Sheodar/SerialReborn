package GUI.optionsWindow;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class OptionsWindowMain {

    public static void showOptionsWindow(Window mod) throws IOException {
        Stage stg = new Stage();

        Parent root = FXMLLoader.load(OptionsWindowMain.class.getResource("OptionsWindow.fxml"));
        Scene scn = new Scene (root);
        stg.setResizable(false);
        stg.setScene(scn);
        stg.setTitle("Настройки");
        stg.initModality(Modality.WINDOW_MODAL);
        stg.initOwner(mod);
        stg.showAndWait();
    }
}
