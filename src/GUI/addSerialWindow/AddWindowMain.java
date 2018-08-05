package GUI.addSerialWindow;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import java.io.IOException;

public class AddWindowMain {

    public static void showAddingWindow(Window mod) throws IOException {
        Stage stg = new Stage();

        Parent root = FXMLLoader.load(AddWindowMain.class.getResource("AddWindow.fxml"));
        Scene scn = new Scene (root);

        stg.setScene(scn);
        stg.setTitle("Создание сериала");
        stg.initModality(Modality.WINDOW_MODAL);
        stg.initOwner(mod);
        stg.showAndWait();
    }
}
