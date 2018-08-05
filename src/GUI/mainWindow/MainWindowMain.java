package GUI.mainWindow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainWindowMain extends Application {

    @Override
    public void start(Stage Window) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Window.setResizable(false);
        Scene z = new Scene (root);
        z.getStylesheets().add("style.css");
        Window.getIcons().add(new Image("images/Icon.png"));
        Window.setHeight(458.0);
        Window.setWidth(656.0);
        Window.setTitle("SerialsGO");
        Window.setScene(z);
        Window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

