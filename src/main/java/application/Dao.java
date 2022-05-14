package application;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;

public interface Dao {

    void checkChoiceBox(ActionEvent event)throws SQLException;
    void checkWeatherChoiceBox(ActionEvent event)throws SQLException;
    void createTable();
    void createWeatherTable();
    void searchBy() throws SQLException;
    void addLocation(ActionEvent event) throws SQLException;
    void clearField(MouseEvent event);
    void switchToStartScene(ActionEvent event) throws IOException;
    void switchToLocationScene(ActionEvent event) throws IOException;
    void switchToLocationShowScene(ActionEvent event) throws IOException;
    void switchToWeatherScene(ActionEvent event) throws IOException;
    void exit(ActionEvent event);
}
