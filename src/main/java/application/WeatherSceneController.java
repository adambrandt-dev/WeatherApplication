package application;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class WeatherSceneController extends DaoImpl implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createWeatherTable();
        filterButton.setOnAction(event -> {
            try {
                checkWChoiceBox(event);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void exit(ActionEvent event){
        ((Stage) (((Node)event.getSource()).getScene().getWindow())).close();
    }


}
