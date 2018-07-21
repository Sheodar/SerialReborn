package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage Window) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample2.fxml"));
        Window.setResizable(false);
        Scene z = new Scene (root);
        z.getStylesheets().add("style.css");
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

