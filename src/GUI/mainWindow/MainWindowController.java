package GUI.mainWindow;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import classes.Serial;
import classes.SerialsSeries;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static DB.ConnectionDB.DBConnect2;
import static GUI.optionsWindow.OptionsWindowMain.showOptionsWindow;
import static allMethodsDВ.GettersSerialMethods.DBGetCurrentSeason;
import static allMethodsDВ.GettersSerialMethods.DBGetCurrentSeries;
import static allMethodsDВ.GettersSerialMethods.getTableSerialsNameForDB;
import static allMethodsDВ.UpdateSerialMethods.*;
import static GUI.settingsSerialWindow.SettingsSerialsWindowMain.showSettingsWindow;
import static GUI.addSerialWindow.AddWindowMain.showAddingWindow;


public class MainWindowController {
    public static ObservableList<Serial> allSerials = FXCollections.observableArrayList();
    public static ObservableList<Integer> qweqweqwe = FXCollections.observableArrayList();
    public static int qwe = 0;
    /**
     * ОБЩИЕ ПЕРЕМЕННЫЕ
     **/
    private ObservableList<SerialsSeries> serialData = FXCollections.observableArrayList();
    private ObservableList<String> seasons = FXCollections.observableArrayList();
    private ObservableList<String> series = FXCollections.observableArrayList();
    private Map<Integer, Integer> serialsMap = new HashMap<>();

    public static Serial pickedSerial;

    /*******************************/
    private static Serial focusedSerial;
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
    private AnchorPane mainPane;
    @FXML
    private TextArea discriptionField;
    @FXML
    private TextArea commentField;
    @FXML
    private AnchorPane startPane;
    @FXML
    private Button helloCreate;

    private void tablSerial() throws SQLException {
        allSerials.clear();
        allSerials.addAll(getTableSerialsNameForDB());
    }

    private void updateSeasone(Serial serial) {
        serialsMap.clear();
        serialData.clear();
        seasons.clear();
        int z = 0;
        for (Iterator<Map.Entry<Integer, Integer>> iterator = serial.getSeries().entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<Integer, Integer> entry = iterator.next();
            seasons.add("Сезон: " + entry.getKey());
            try {
                if (entry.getKey() > Integer.parseInt(serial.getCurrentSeason())) {
                    z++;
                }
            } catch (NumberFormatException e) {
                z = 0;
            }

            for (int x = 0; x < entry.getValue(); x++) {
                serialData.add(new SerialsSeries(entry.getKey(), x + 1));
                serialsMap.put(entry.getKey(), entry.getValue());
            }
        }
        focusedSerial = serial;
        choiseSeason.setItems(seasons);
        choiseSeason.getSelectionModel().select(DBGetCurrentSeason(serial.getName()) - 1);
    }


    private void updateSeries() {
        if (serialsMap.size() > 0) {
            series.clear();
            int seasonNumber = Integer.parseInt(choiseSeason.valueProperty().get().substring(choiseSeason.valueProperty().get().length() - 1, choiseSeason.valueProperty().get().length()));
            for (SerialsSeries aSerialData : serialData) {
                if (aSerialData.getSeasonNumbed() == seasonNumber) {
                    series.add("Серия: " + aSerialData.getSeriesNumbed());
                }
            }
            choiseSeries.setItems(series);
            if (qwe > 0) {
                choiseSeries.getSelectionModel().select(0);
            }
        }

    }

    @FXML
    private void initialize() throws SQLException {

        allTable.getStyleClass().add("hide-header");
        try {
            DBConnect2();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tablSerial();

        ImageView imageSetting = new ImageView(new Image("images/settings2.png"));
        imageSetting.setFitHeight(settingButton.getPrefHeight() - 10);
        imageSetting.setFitWidth(settingButton.getPrefWidth() - 10);
        settingButton.graphicProperty().setValue(imageSetting);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allTable.setItems(allSerials);

        choiseSeason.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, //
                                String oldValue, String newValue) {
                if (newValue != null) {
                    updateSeries();
                    updateCurrentSeason(allTable.getItems().get(allTable.getSelectionModel().getSelectedIndex()).getName(), choiseSeason.getItems().get(choiseSeason.getSelectionModel().getSelectedIndex()));
                    qwe++;
                }
            }
        });

        choiseSeries.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, //
                                String oldValue, String newValue) {
                if (newValue != null) {
                    updateCurrentSeries(allTable.getItems().get(allTable.getSelectionModel().getSelectedIndex()).getName(), choiseSeries.getItems().get(choiseSeries.getSelectionModel().getSelectedIndex()));
                }
            }
        });


        allTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                qwe = 0;
                serialsField.setVisible(true);
                startPane.setVisible(false);
                pickedSerial = (allTable.getItems().get(allTable.getSelectionModel().getSelectedIndex()));

                commentField.setText(pickedSerial.getComment());
                discriptionField.setText(pickedSerial.getDiscription());

                updateSeasone(pickedSerial);
                choiseSeries.getSelectionModel().select(DBGetCurrentSeries(pickedSerial.getName()) - 1);
            }
        });

        openSerial.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                System.out.println(mainPane.getWidth());
            }
        });

        fileAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showAddingWindow(openSerial.getScene().getWindow());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        helloCreate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showAddingWindow(openSerial.getScene().getWindow());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        optionsBar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showOptionsWindow(openSerial.getScene().getWindow());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        settingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showSettingsWindow(openSerial.getScene().getWindow());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        allSerials.addListener(new ListChangeListener<Serial>() {
            @Override
            public void onChanged(Change<? extends Serial> c) {
                allTable.setItems(allSerials);
            }
        });
        qweqweqwe.addListener(new ListChangeListener<Integer>() {
            @Override
            public void onChanged(Change<? extends Integer> c) {
                serialsField.setVisible(false);
                startPane.setVisible(true);
            }
        });

        commentField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (t1 != null)
                    pickedSerial.setComment(t1);
                updateComment(pickedSerial.getIdSerialDB(), t1);
            }
        });

        discriptionField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (t1 != null)
                    pickedSerial.setDiscription(t1);
                updateDiscription(pickedSerial.getIdSerialDB(), t1);
            }
        });

    }
}