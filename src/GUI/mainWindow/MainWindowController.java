package GUI.mainWindow;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import classes.Serial;
import classes.SerialsSeries;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static DB.ConnectionDB.DBConnect;
import static GUI.optionsWindow.OptionsWindowMain.showOptionsWindow;
import static allMethodsDВ.GettersBrowsersMethods.DBGetCurrentBrowserPath;
import static allMethodsDВ.GettersSerialMethods.DBGetCurrentSeason;
import static allMethodsDВ.GettersSerialMethods.DBGetCurrentSeries;
import static allMethodsDВ.GettersSerialMethods.getTableSerialsNameForDB;
import static allMethodsDВ.UpdateSerialMethods.*;
import static GUI.settingsSerialWindow.SettingsSerialsWindowMain.showSettingsWindow;
import static GUI.addSerialWindow.AddWindowMain.showAddingWindow;


public class MainWindowController {
    public static ObservableList<Serial> allSerials = FXCollections.observableArrayList();
    public static ObservableList<Integer> checkerdel = FXCollections.observableArrayList();
    private static int qwe = 0;

    /**************************************************************************************************************/

    private ObservableList<SerialsSeries> serialData = FXCollections.observableArrayList();
    private ObservableList<String> seasons = FXCollections.observableArrayList();
    private ObservableList<String> series = FXCollections.observableArrayList();
    private Map<Integer, Integer> serialsMap = new HashMap<>();

    public static Serial pickedSerial;
    private static int pickedRow;

    /**************************************************************************************************************/
    @FXML
    private TableView<Serial> allTable;
    @FXML
    private TableColumn<Serial, String> nameColumn;
    @FXML
    private ChoiceBox<String> choiseSeason;
    @FXML
    private ChoiceBox<String> choiseSeries;
    @FXML
    private Button openSerial;
    @FXML
    private Button settingButton;
    @FXML
    private MenuItem fileAdd;
    @FXML
    private MenuItem optionsBar;
    @FXML
    private AnchorPane serialsField;
    @FXML
    private TextArea discriptionField;
    @FXML
    private TextArea commentField;
    @FXML
    private AnchorPane startPane;
    @FXML
    private Button helloCreate;

/**************************************************************************************************************/
/**************************************************************************************************************/

    private void tablSerial() {
        allSerials.clear();
        allSerials.addAll(getTableSerialsNameForDB());
    }

/**************************************************************************************************************/

    private void openerURL(String URL, String path) {
        if (Objects.equals(path, "1")) {
            Desktop desktop = null;
            if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();
            }
            try {
                URI url = new URI(URL);
                if (desktop != null) {
                    desktop.browse(url);
                }
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
        } else {
            String request = String.format("%s %s", path, URL);
            Runtime runtime = Runtime.getRuntime();

            try {
                runtime.exec(request);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ой!");
                alert.setHeaderText(null);
                alert.setContentText("Что-то пошло не так, надо проверить настройки браузеров в Файл -> Настройки");
                alert.showAndWait();
            }

        }
    }

/**************************************************************************************************************/

    private void updateSeasone(Serial serial) {
        serialsMap.clear();
        serialData.clear();
        seasons.clear();
        for (Iterator<Map.Entry<Integer, Integer>> iterator = serial.getSeries().entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<Integer, Integer> entry = iterator.next();
            seasons.add("Сезон: " + entry.getKey());
            for (int x = 0; x < entry.getValue(); x++) {
                serialData.add(new SerialsSeries(String.valueOf(entry.getKey()), String.valueOf(x + 1)));
                serialsMap.put(entry.getKey(), entry.getValue());
            }
        }
        choiseSeason.setItems(seasons);
        choiseSeason.getSelectionModel().select(DBGetCurrentSeason(serial.getName()) - 1);
    }

/**************************************************************************************************************/

    private void updateSeries() {
        if (serialsMap.size() > 0) {
            series.clear();
            int seasonNumber = Integer.parseInt(choiseSeason.valueProperty().get().replaceAll("Сезон: ","").trim());
            for (SerialsSeries aSerialData : serialData) {
                if (Integer.parseInt(aSerialData.getSeasonNumber()) == seasonNumber) {
                    series.add("Серия: " + aSerialData.getSeriesNumber());
                }
            }
            choiseSeries.setItems(series);
            if (qwe > 0) {
                choiseSeries.getSelectionModel().select(0);
            }
        }

    }

/**************************************************************************************************************/
/**************************************************************************************************************/

    @FXML
    private void initialize() {
        allTable.getStyleClass().add("hide-header");
        allTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        DBConnect();
        tablSerial();
        ImageView imageSetting = new ImageView(new Image("images/settings2.png"));
        imageSetting.setFitHeight(settingButton.getPrefHeight() - 10);
        imageSetting.setFitWidth(settingButton.getPrefWidth() - 10);
        settingButton.graphicProperty().setValue(imageSetting);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allTable.setItems(allSerials);

/**************************************************************************************************************/

        choiseSeason.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateSeries();
                updateCurrentSeason(allTable.getItems().get(allTable.getSelectionModel().getSelectedIndex()).getName(), choiseSeason.getItems().get(choiseSeason.getSelectionModel().getSelectedIndex()));
                qwe++;
            }
        });

/**************************************************************************************************************/

        //
        choiseSeries.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateCurrentSeries(allTable.getItems().get(allTable.getSelectionModel().getSelectedIndex()).getName(), choiseSeries.getItems().get(choiseSeries.getSelectionModel().getSelectedIndex()));
            }
        });

/**************************************************************************************************************/

        allTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                qwe = 0;
                serialsField.setVisible(true);
                startPane.setVisible(false);
                pickedSerial = (allTable.getItems().get(allTable.getSelectionModel().getSelectedIndex()));
                pickedRow  = allTable.getSelectionModel().getSelectedIndex();
                commentField.setText(pickedSerial.getComment());
                discriptionField.setText(pickedSerial.getDiscription());
                updateSeasone(pickedSerial);
                choiseSeries.getSelectionModel().select(DBGetCurrentSeries(pickedSerial.getName()) - 1);
            }
        });

/**************************************************************************************************************/

        openSerial.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            openerURL(pickedSerial.getPath(), DBGetCurrentBrowserPath());
        });

/**************************************************************************************************************/

        fileAdd.setOnAction(event -> {
            try {
                showAddingWindow(openSerial.getScene().getWindow());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

/**************************************************************************************************************/

        helloCreate.setOnAction(event -> {
            try {
                showAddingWindow(openSerial.getScene().getWindow());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

/**************************************************************************************************************/

        optionsBar.setOnAction(event -> {
            try {
                showOptionsWindow(openSerial.getScene().getWindow());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

/**************************************************************************************************************/

        settingButton.setOnAction(event -> {
            try {
                showSettingsWindow(openSerial.getScene().getWindow());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

/**************************************************************************************************************/

        allSerials.addListener((ListChangeListener<Serial>) c -> {
            allTable.setItems(allSerials);
            allTable.getSelectionModel().select(pickedRow);
        });

/**************************************************************************************************************/

        checkerdel.addListener((ListChangeListener<Integer>) c -> {
                serialsField.setVisible(false);
                startPane.setVisible(true);
        });

/**************************************************************************************************************/

        commentField.textProperty().addListener((ov, t, t1) -> {
            if (t1 != null)
                pickedSerial.setComment(t1);
            updateComment(pickedSerial.getIdSerialDB(), t1);
        });

/**************************************************************************************************************/

        discriptionField.textProperty().addListener((ov, t, t1) -> {
            if (t1 != null)
                pickedSerial.setDiscription(t1);
            updateDiscription(pickedSerial.getIdSerialDB(), t1);
        });

/**************************************************************************************************************/

    }
}