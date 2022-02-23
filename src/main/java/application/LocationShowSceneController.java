package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LocationShowSceneController extends DaoImpl implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createTable();
        filterButton.setOnAction(event -> {
            try {
                checkChoiceBox(event);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
