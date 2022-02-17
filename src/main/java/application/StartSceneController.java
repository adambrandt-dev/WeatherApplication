package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartSceneController {

    private Stage stage;
    private Scene scene;
    private Parent fxmlLoader;


    public void switchToLocationShowScene(ActionEvent event) throws IOException {
        fxmlLoader  = FXMLLoader.load(getClass().getResource("LocationShowScene.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToWeatherScene(ActionEvent event) throws IOException {
        fxmlLoader  = FXMLLoader.load(getClass().getResource("WeatherScene.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.show();
    }

    public void exit(ActionEvent event){
        ((Stage) (((Node)event.getSource()).getScene().getWindow())).close();
    }

}