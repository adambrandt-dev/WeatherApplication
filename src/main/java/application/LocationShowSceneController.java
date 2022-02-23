package application;

import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LocationShowSceneController extends DaoImpl implements Initializable {

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
}
