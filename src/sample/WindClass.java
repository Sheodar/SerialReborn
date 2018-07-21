package sample;

import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;
import java.io.IOException;
import sample.addWindow;

class WindClass {

    static void showWindow(Window mod) throws IOException {
        Stage stg = new Stage();

        Parent root = FXMLLoader.load(WindClass.class.getResource("addWindow.fxml"));
        Scene scn = new Scene (root);

        stg.setScene(scn);
        stg.setTitle("Создание сериала");
        stg.initModality(Modality.WINDOW_MODAL);
        stg.initOwner(mod);
        stg.showAndWait();
    }
}
