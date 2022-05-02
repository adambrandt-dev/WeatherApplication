package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LocationSceneController extends DaoImpl implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createTable();
        addButton.setOnAction(event -> {
            try {
                addLocation(event);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

}
