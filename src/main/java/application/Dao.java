package application;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;

public interface Dao {

    void checkChoiceBox();
    void createTable();
    void searchBy() throws SQLException;
    void addNew();
    void clearField(MouseEvent event);
    void switchToStartScene(ActionEvent event) throws IOException;
    void switchToLocationScene(ActionEvent event) throws IOException;
    void switchToLocationShowScene(ActionEvent event) throws IOException;
    void switchToWeatherScene(ActionEvent event) throws IOException;
    void exit(ActionEvent event);
}
