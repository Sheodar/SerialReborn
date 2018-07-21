package GUI.settingsSerialWindow;

import classes.TimeSeason;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import static allMethodsDВ.SerialsMethods.removeSerial;
import static allMethodsDВ.UpdateSerialMethods.updateName;
import static allMethodsDВ.UpdateSerialMethods.updatePath;
import static allMethodsDВ.UpdateSerialMethods.updateSeasons;
import static methods.MethodsClass.getTableSerialsNameForDB;
import static GUI.mainWindow.MainWindowController.allSerials;
import static GUI.mainWindow.MainWindowController.pickedSerial;


public class SettingsSerialWindowController {
/**************************************************************************************************************/
@FXML
private Button deleteButtonChange;

    @FXML
    private TextField fieldNameChange;
    @FXML
    private TextField fieldPathChange;
    @FXML
    private TextField fieldPictureChange;
    @FXML
    private Button searchPictureChange;
    @FXML
    private Button addSeasonChange;
    @FXML
    private TableView<TimeSeason> seasonsTableChange;
    @FXML
    private TableColumn<TimeSeason, String> seasonColumnChange;
    @FXML
    private TableColumn<TimeSeason, String> seriesColumnChange;
    @FXML
    private TableColumn<TimeSeason, String> removeLineChange;
    @FXML
    private Button saveChangesChange;
    @FXML
    private Button backButtonChange;
    @FXML
    private Label mainError;
    @FXML
    private Label pathNameErrorLabel;


/**************************************************************************************************************/
    private int seasonsBreaker = 0;
    private boolean bPath = true;
    private ObservableList<TimeSeason> allSeasons = FXCollections.observableArrayList();

/**************************************************************************************************************/
    private void print(Object input) {
    System.out.println(String.valueOf(input));
}


/**************************************************************************************************************/
    private void refreshDeleteBuntton() {
        for (int x = 0; x < allSeasons.size(); x++) {
            if (x == allSeasons.size() - 1) {
                allSeasons.get(x).getButton().setVisible(true);
            } else {
                allSeasons.get(x).getButton().setVisible(false);
            }
        }
    }
/**************************************************************************************************************/
    private void printAllSeason(){

        for (Iterator<Map.Entry<Integer, Integer>> iterator = pickedSerial.getSeries().entrySet().iterator(); iterator.hasNext(); ) {
            seasonsBreaker++;
            Map.Entry<Integer, Integer> entry = iterator.next();
            allSeasons.add(new TimeSeason(String.valueOf(entry.getKey()), String.valueOf(entry.getValue())));
        }
        seasonsTableChange.setItems(allSeasons);
        for (int x = 0; x < allSeasons.size(); x++) {
            int finalX = x;
            allSeasons.get(x).getButton().setFocusTraversable(false);
            allSeasons.get(x).getButton().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    allSeasons.remove(finalX);
                    refreshDeleteBuntton();
                    seasonsBreaker--;
                }
            });
            refreshDeleteBuntton();
        }
    }

/**************************************************************************************************************/
/**************************************************************************************************************/

    @FXML
    private void initialize() throws SQLException {

        seasonColumnChange.setCellValueFactory(new PropertyValueFactory<>("seasonNumber"));
        seriesColumnChange.setCellValueFactory(new PropertyValueFactory<>("seriesNumber"));
        removeLineChange.setCellValueFactory(new PropertyValueFactory<>("button"));

        seriesColumnChange.setCellFactory(TextFieldTableCell.<TimeSeason>forTableColumn());

        seriesColumnChange.setOnEditCommit((TableColumn.CellEditEvent<TimeSeason, String> event) -> {
            TablePosition<TimeSeason, String> pos = event.getTablePosition();
            String newSeriesValue = event.getNewValue();
            int row = pos.getRow();
            TimeSeason game = event.getTableView().getItems().get(row);
            game.setSeriesNumber(newSeriesValue);
        });
        printAllSeason();


        fieldNameChange.setText(pickedSerial.getName());
        fieldPathChange.setText(pickedSerial.getPath());
//        fieldPictureChange.getText(); TODO пока не работает (и не будет :с )
/**************************************************************************************************************/
        addSeasonChange.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            seasonsBreaker++;
            allSeasons.add(new TimeSeason(String.valueOf(seasonsBreaker), "0"));
            for (int x = 0; x < allSeasons.size(); x++) {
                int finalX = x;
                allSeasons.get(x).getButton().setFocusTraversable(false);
                allSeasons.get(x).getButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        allSeasons.remove(finalX);
                        refreshDeleteBuntton();
                        seasonsBreaker--;
                    }
                });
                refreshDeleteBuntton();
            }
            seasonsTableChange.setItems(allSeasons);
        });
/**************************************************************************************************************/
        backButtonChange.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            seasonsBreaker = 0;
            bPath = false;
            allSeasons.clear();
            fieldPathChange.setText("");
            fieldNameChange.setText("");
            pathNameErrorLabel.setVisible(false);
            mainError.setVisible(false);
            Stage x = (Stage) backButtonChange.getScene().getWindow();
            x.close();
        });
/**************************************************************************************************************/
        fieldPathChange.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                String[] checkUrlFirst = t1.split("://");
                switch (checkUrlFirst[0]) {
                    case "http":
                        bPath = true;
                        pathNameErrorLabel.setVisible(false);
                        break;
                    case "https":
                        bPath = true;
                        pathNameErrorLabel.setVisible(false);
                        break;
                    default:
                        String[] checkUrlSecond = t1.split("\\.");
                        if (checkUrlSecond[0].equals("www")) {
                            bPath = true;
                            pathNameErrorLabel.setVisible(false);
                        } else {
                            bPath = false;
                            pathNameErrorLabel.setVisible(true);
                        }
                        break;
                }
                if (fieldNameChange.getText().length() >= 1) {
                    mainError.setVisible(false);
                } else {
                    mainError.setVisible(true);
                }
            }
        });
/**************************************************************************************************************/
        fieldNameChange.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (fieldPathChange.getText().length() >= 1) {
                    mainError.setVisible(false);
                } else {
                    mainError.setVisible(true);
                }
            }
        });



/**************************************************************************************************************/
        saveChangesChange.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!mainError.isVisible() && bPath) {
                    updateName(pickedSerial.getIdSerialDB(), fieldNameChange.getText());
                    updatePath(pickedSerial.getIdSerialDB(), fieldPathChange.getText());
                    updateSeasons(pickedSerial.getIdSerialDB(), allSeasons);


                    allSerials.clear();
                    seasonsBreaker = 0;
                    bPath = false;
                    allSeasons.clear();
                    fieldPathChange.setText("");
                    fieldNameChange.setText("");
                    pathNameErrorLabel.setVisible(false);
                    mainError.setVisible(false);
                    Stage x = (Stage) backButtonChange.getScene().getWindow();
                    x.close();
                    try {
                        allSerials.addAll(getTableSerialsNameForDB());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }else {
                    mainError.setVisible(true);
                }
            }
        });
/**************************************************************************************************************/
        deleteButtonChange.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeSerial(Integer.parseInt(pickedSerial.getIdSerialDB()));
                allSerials.clear();
                seasonsBreaker = 0;
                bPath = false;
                allSeasons.clear();
                fieldPathChange.setText("");
                fieldNameChange.setText("");
                pathNameErrorLabel.setVisible(false);
                mainError.setVisible(false);
                Stage x = (Stage) backButtonChange.getScene().getWindow();
                x.close();
                try {
                    allSerials.addAll(getTableSerialsNameForDB());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

/**************************************************************************************************************/


    }
}