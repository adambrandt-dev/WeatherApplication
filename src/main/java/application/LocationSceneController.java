package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LocationSceneController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent fxmlLoader;

    @FXML
    private TextField latitudeAddField;
    @FXML
    private TextField longitudeAddField;
    @FXML
    private TextField regionAddField;
    @FXML
    private TextField countryAddField;
    @FXML
    private Button addButton;

    @FXML
    private TableView<LocationTable> table;
    @FXML
    private TableColumn<LocationTable, Integer> loc_id;
    @FXML
    private TableColumn<LocationTable, Double> loc_latitude;
    @FXML
    private TableColumn<LocationTable, Double> loc_longitude;
    @FXML
    private TableColumn<LocationTable, String> loc_region;
    @FXML
    private TableColumn<LocationTable, String> loc_country;


    String query;
    ObservableList<LocationTable> observableList = FXCollections.observableArrayList();
    DBConnector dbConnector = new DBConnector();

    public static final int LATITUDE_PARAMETER_INDEX = 1;
    public static final int LONGITUDE_PARAMETER_INDEX = 2;
    public static final int REGION_PARAMETER_INDEX = 3;
    public static final int COUNTRY_PARAMETER_INDEX = 4;


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

    public void createTable() {
        table.getItems().clear();
        query = "Select * from weather.weather_localization;";

        try {
            Statement statement = dbConnector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                observableList.add(new LocationTable(resultSet.getInt("country_id"), resultSet.getDouble("latitude"),
                        resultSet.getDouble("longitude"), resultSet.getString("region"), resultSet.getString("country_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        loc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        loc_latitude.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        loc_longitude.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        loc_region.setCellValueFactory(new PropertyValueFactory<>("region"));
        loc_country.setCellValueFactory(new PropertyValueFactory<>("country"));

        table.setItems(observableList);
    }


    public void addLocation(ActionEvent event) throws SQLException {
        query = "INSERT INTO weather.weather_localization (latitude, longitude, region, country_name) VALUES (?, ?, ?, ?);";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBConnector
                    .getConnection()
                    .prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        preparedStatement.setString(LATITUDE_PARAMETER_INDEX, latitudeAddField.getText());
        preparedStatement.setString(LONGITUDE_PARAMETER_INDEX, longitudeAddField.getText());
        preparedStatement.setString(REGION_PARAMETER_INDEX, regionAddField.getText());
        preparedStatement.setString(COUNTRY_PARAMETER_INDEX, countryAddField.getText());

        preparedStatement.executeUpdate();
        createTable();
    }

    public void switchToLocationShowScene(ActionEvent event) throws IOException {
        fxmlLoader = FXMLLoader.load(getClass().getResource("LocationShowScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.show();
    }

}
