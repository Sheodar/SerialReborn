package GUI.addSerialWindow;

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

import static allMethodsDВ.SerialsMethods.addSerial;
import static methods.MethodsClass.getTableSerialsNameForDB;
import static GUI.mainWindow.MainWindowController.allSerials;


public class AddWindowController {
    /**************************************************************************************************************/

    @FXML
    private Button addSeason;
    @FXML
    private TableView seasonsTable;
    @FXML
    private TableColumn seasonColumn;
    @FXML
    private TableColumn<TimeSeason, String> seriesColumn;
    @FXML
    private TableColumn removeLine;
    @FXML
    private TextField nameSerialField;
    @FXML
    private TextField pathSerialField;
    @FXML
    private TextField pictureSerialField;
    @FXML
    private Button searchButton;
    @FXML
    private Button createButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label pathNameErrorLabel;
    @FXML
    private Label mainError;

/**************************************************************************************************************/

    private int seasonsBreaker = 0;
    private boolean bPath = false;
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
/**************************************************************************************************************/

    @FXML
    private void initialize() throws SQLException {
        seasonColumn.setCellValueFactory(new PropertyValueFactory<>("seasonNumber"));
        seriesColumn.setCellValueFactory(new PropertyValueFactory<>("seriesNumber"));
        removeLine.setCellValueFactory(new PropertyValueFactory<>("button"));

        seriesColumn.setCellFactory(TextFieldTableCell.<TimeSeason>forTableColumn());

        seriesColumn.setOnEditCommit((TableColumn.CellEditEvent<TimeSeason, String> event) -> {
            TablePosition<TimeSeason, String> pos = event.getTablePosition();
            String newSeriesValue = event.getNewValue();
            int row = pos.getRow();
            TimeSeason game = event.getTableView().getItems().get(row);
            game.setSeriesNumber(newSeriesValue);
        });

/**************************************************************************************************************/

        addSeason.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
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
            seasonsTable.setItems(allSeasons);
        });

/**************************************************************************************************************/

        cancelButton.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            seasonsBreaker = 0;
            bPath = false;
            allSeasons.clear();
            pathSerialField.setText("");
            nameSerialField.setText("");
            pathNameErrorLabel.setVisible(false);
            mainError.setVisible(false);
            Stage x = (Stage) cancelButton.getScene().getWindow();
            x.close();
        });

/**************************************************************************************************************/

        pathSerialField.textProperty().addListener(new ChangeListener<String>() {
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
                if (nameSerialField.getText().length() >= 1) {
                    mainError.setVisible(false);
                } else {
                    mainError.setVisible(true);
                }
            }
        });

/**************************************************************************************************************/

        nameSerialField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (pathSerialField.getText().length() >= 1) {
                    mainError.setVisible(false);
                } else {
                    mainError.setVisible(true);
                }
            }
        });

/**************************************************************************************************************/

        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!mainError.isVisible() && bPath) {
                    String name = nameSerialField.getText();
                    String path = pathSerialField.getText();
                    addSerial(name, path, allSeasons);
                    allSerials.clear();
                    seasonsBreaker = 0;
                    bPath = false;
                    allSeasons.clear();
                    pathSerialField.setText("");
                    nameSerialField.setText("");
                    pathNameErrorLabel.setVisible(false);
                    mainError.setVisible(false);
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

    }
}