package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LocationShowSceneController extends DaoImpl implements Initializable {

//    @FXML
//    private TextField locationSearchField;
//

//    public static final int SEARCH_PARAMETER_INDEX = 1;

    @FXML
    private TableView tableView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filterButton.setOnAction(event -> {
            try {
                checkChoiceBox(event);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        createTable();
    }

    @Override
    public void createTable() {
        super.createTable();
    }

    @Override
    public void checkChoiceBox(ActionEvent event) throws SQLException {
        super.checkChoiceBox(event);
    }

    @Override
    public void searchBy() throws SQLException {
        super.searchBy();
    }

    @Override
    public void clearField(MouseEvent event) {
        super.clearField(event);
    }

    @Override
    public void switchToStartScene(ActionEvent event) throws IOException {
        super.switchToStartScene(event);
    }

    @Override
    public void switchToLocationScene(ActionEvent event) throws IOException {
        super.switchToLocationScene(event);
    }

    @Override
    public void exit(ActionEvent event) {
        super.exit(event);
    }
}
