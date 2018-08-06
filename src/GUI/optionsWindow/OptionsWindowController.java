package GUI.optionsWindow;

import classes.Browser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;
import java.util.Objects;

import static allMethodsDВ.BrowsersMethods.addBrowser;
import static allMethodsDВ.BrowsersMethods.removeBrowser;
import static allMethodsDВ.GettersBrowsersMethods.DBGetCurrentBrowserID;
import static allMethodsDВ.GettersBrowsersMethods.getTableBrowsersNameForDB;
import static allMethodsDВ.UpdateBrowsersMethods.updateCurrentBrowser;
import static allMethodsDВ.UpdateBrowsersMethods.updateName;
import static allMethodsDВ.UpdateBrowsersMethods.updatePath;
import static javafx.scene.control.cell.TextFieldTableCell.forTableColumn;


public class OptionsWindowController {
    /**************************************************************************************************************/
    @FXML
    private ChoiceBox<String> pickedBrowser;
    @FXML
    private TableColumn<Browser, String> nameBrowserT;
    @FXML
    private TableColumn<Browser, String> pathBrowserT;
    @FXML
    private TableColumn buttonBrowserT;
    @FXML
    private TableColumn removeBrowserT;
    @FXML
    private Button backOptionsButton;
    @FXML
    private Button addBrowserButton;
    @FXML
    private TableView<Browser> allBrowserT;


    /**************************************************************************************************************/

    private int seasonsBreaker = 0;
    private boolean bPath = true;
    private ObservableList<Browser> allBrowser = FXCollections.observableArrayList();
    private ObservableList<String> allBrowsersName = FXCollections.observableArrayList();

    /**************************************************************************************************************/

    private static String getFileExtension(String mystr) {
        int index = mystr.lastIndexOf('.');
        return index == -1 ? null : mystr.substring(index);
    }

    /**************************************************************************************************************/

    private void setStyleButton(Button button) {
        button.setFocusTraversable(false);
        button.setMinHeight(20.0);
        button.setPrefHeight(20.0);
        button.setMaxHeight(20.0);
        button.setPadding(new Insets(1, 8, 1, 8));
    }

    /**************************************************************************************************************/
    private void updateChoiseBoxBrowsers() {
        if (allBrowsersName.size() > 0) {
            allBrowsersName.clear();
        }
        if (allBrowser.size() > 0) {
            for (int x = 0; x < allBrowser.size(); x++) {
                allBrowsersName.add(allBrowser.get(x).getName());
            }
        }
        pickedBrowser.setItems(allBrowsersName);
        if (allBrowsersName.size() > 0) {
            for (int z = 0; z < allBrowser.size(); z++) {
                if (allBrowser.get(z).getIdBrowserDB() == DBGetCurrentBrowserID()) {
                    pickedBrowser.getSelectionModel().select(z);
                }
            }
        }
    }

    /**************************************************************************************************************/

    private void printAllBrowsers() {
        if (allBrowser.size() > 0) {
            allBrowser.clear();
        }
        allBrowser.addAll(getTableBrowsersNameForDB());
        allBrowserT.setItems(allBrowser);

        for (int x = 0; x < allBrowser.size(); x++) {
            int finalX = x;
            Button button = allBrowser.get(x).getGetPathButton();
            setStyleButton(button);
            Button finalButton = button;
            int finalX1 = x;
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.exe", "*.exe");
                    fileChooser.getExtensionFilters().add(extFilter);
                    File file = fileChooser.showOpenDialog(finalButton.getScene().getWindow());
                    if (file != null) {
                        if (Objects.equals(getFileExtension(file.getName()), ".exe")) {
                            updatePath(allBrowser.get(finalX1).getName(), file.getAbsolutePath());
                            printAllBrowsers();
                            updateChoiseBoxBrowsers();
                        }

                    }
                }
            });
            button = allBrowser.get(x).getRemoveButton();
            setStyleButton(button);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    removeBrowser(allBrowser.get(finalX).getIdBrowserDB());
                    printAllBrowsers();
                    updateChoiseBoxBrowsers();
                }
            });
        }
    }

    /**************************************************************************************************************/
    /**************************************************************************************************************/
    @FXML
    private void initialize() throws SQLException {
        nameBrowserT.setCellValueFactory(new PropertyValueFactory<>("name"));
        pathBrowserT.setCellValueFactory(new PropertyValueFactory<>("path"));
        buttonBrowserT.setCellValueFactory(new PropertyValueFactory<>("getPathButton"));
        removeBrowserT.setCellValueFactory(new PropertyValueFactory<>("removeButton"));

        nameBrowserT.setCellFactory(TextFieldTableCell.<Browser>forTableColumn());
        nameBrowserT.setOnEditCommit((TableColumn.CellEditEvent<Browser, String> event) -> {
            TablePosition<Browser, String> pos = event.getTablePosition();
            String newNameValue = event.getNewValue();
            int row = pos.getRow();
            Browser pick = event.getTableView().getItems().get(row);
            pick.setName(newNameValue);
            updateName(String.valueOf(pick.getIdBrowserDB()), newNameValue);
            updateChoiseBoxBrowsers();
        });

        printAllBrowsers();
        updateChoiseBoxBrowsers();

/**************************************************************************************************************/

        addBrowserButton.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            addBrowser("Задать имя", "");
            printAllBrowsers();
            updateChoiseBoxBrowsers();
        });

/**************************************************************************************************************/
        backOptionsButton.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            seasonsBreaker = 0;
            bPath = false;
            Stage x = (Stage) backOptionsButton.getScene().getWindow();
            x.close();
        });

/**************************************************************************************************************/

        pickedBrowser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, //
                                String oldValue, String newValue) {
                if (newValue != null) {
                    updateCurrentBrowser(pickedBrowser.getItems().get(pickedBrowser.getSelectionModel().getSelectedIndex()));
                }
            }
        });

/**************************************************************************************************************/

    }
}