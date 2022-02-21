package application;

import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class LocationSceneController extends DaoImpl implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createTable();
        addButton.setOnAction(event -> {
            try {
                addNew(event);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }
}
