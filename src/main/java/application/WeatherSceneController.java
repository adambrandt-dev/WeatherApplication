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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

public class WeatherSceneController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent fxmlLoader;

    @FXML
    private CheckBox countryChoiceBox;
    @FXML
    private CheckBox regionChoiceBox;
    @FXML
    private CheckBox dateChoiceBox;
    @FXML
    private CheckBox latitudeChoiceBox;
    @FXML
    private CheckBox longitudeChoiceBox;
    @FXML
    private CheckBox temperatureChoiceBox;
    @FXML
    private CheckBox pressureChoiceBox;
    @FXML
    private CheckBox humidityChoiceBox;
    @FXML
    private CheckBox windDirectionChoiceBox;
    @FXML
    private CheckBox windSpeedChoiceBox;

    @FXML
    private TextField weatherSearchField;
    @FXML
    private Button filterButton;

    @FXML
    private TableView<WeatherTable> table;
    @FXML
    private TableColumn<WeatherTable, String> wea_country;
    @FXML
    private TableColumn<WeatherTable, String> wea_region;
    @FXML
    private TableColumn<WeatherTable, Date> wea_date;
    @FXML
    private TableColumn<WeatherTable, Double> wea_latitude;
    @FXML
    private TableColumn<WeatherTable, Double> wea_longitude;
    @FXML
    private TableColumn<WeatherTable, Double> wea_temperature;
    @FXML
    private TableColumn<WeatherTable, Double> wea_pressure;
    @FXML
    private TableColumn<WeatherTable, String> wea_humidity;
    @FXML
    private TableColumn<WeatherTable, String> wea_wind_direction;
    @FXML
    private TableColumn<WeatherTable, Double> wea_wind_speed;

    public static final int SEARCH_PARAMETER_INDEX = 1;


    String query;
    ObservableList<WeatherTable> observableList = FXCollections.observableArrayList();
    DBConnector dbConnector = new DBConnector();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createTable();
    }

    public void createTable() {
        table.getItems().clear();
        query = "select weather_localization.country_name, weather_localization.region, weather_date, weather_localization.latitude, weather_localization.longitude," +
                " temperature, pressure, humidity, wind_direction, wind_speed from weather_data join weather_localization where weather_localization.country_id = weather_country_id;";

        try {
            Statement statement = dbConnector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                observableList.add(new WeatherTable(resultSet.getString("country_name"), resultSet.getString("region"), resultSet.getDate("weather_Date"),
                        resultSet.getDouble("latitude"), resultSet.getDouble("longitude"), resultSet.getDouble("temperature"),
                        resultSet.getDouble("pressure"), resultSet.getString("humidity"), resultSet.getString("wind_direction"), resultSet.getDouble("wind_speed")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        wea_country.setCellValueFactory(new PropertyValueFactory<>("country"));
        wea_region.setCellValueFactory(new PropertyValueFactory<>("region"));
        wea_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        wea_latitude.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        wea_longitude.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        wea_temperature.setCellValueFactory(new PropertyValueFactory<>("temperature"));
        wea_pressure.setCellValueFactory(new PropertyValueFactory<>("pressure"));
        wea_humidity.setCellValueFactory(new PropertyValueFactory<>("humidity"));
        wea_wind_direction.setCellValueFactory(new PropertyValueFactory<>("windDirection"));
        wea_wind_speed.setCellValueFactory(new PropertyValueFactory<>("windSpeed"));

        table.setItems(observableList);
    }

    public void checkChoiceBox(ActionEvent event) throws SQLException {
        if (countryChoiceBox.isSelected() && filterButton.isArmed()) {
            table.getItems().clear();
            query = "select weather_localization.country_name, weather_localization.region, weather_date, weather_localization.latitude, weather_localization.longitude," +
                    " temperature, pressure, humidity, wind_direction, wind_speed from weather_data join weather_localization where weather_localization.country_id = weather_country_id and weather_localization.country_name = ?;";
            searchBy();

        } else if (regionChoiceBox.isSelected() && filterButton.isArmed()) {
            table.getItems().clear();
            query = "select weather_localization.country_name, weather_localization.region, weather_date, weather_localization.latitude, weather_localization.longitude," +
                    " temperature, pressure, humidity, wind_direction, wind_speed from weather_data join weather_localization where weather_localization.country_id = weather_country_id and weather_localization.region = ?;";
            searchBy();
        } else if (dateChoiceBox.isSelected() && filterButton.isArmed()) {
            table.getItems().clear();
            query = "select weather_localization.country_name, weather_localization.region, weather_date, weather_localization.latitude, weather_localization.longitude," +
                    " temperature, pressure, humidity, wind_direction, wind_speed from weather_data join weather_localization where weather_localization.country_id = weather_country_id and weather_date = ?;";
            searchBy();
        } else if (latitudeChoiceBox.isSelected() && filterButton.isArmed()) {
            table.getItems().clear();
            query = "select weather_localization.country_name, weather_localization.region, weather_date, weather_localization.latitude, weather_localization.longitude," +
                    " temperature, pressure, humidity, wind_direction, wind_speed from weather_data join weather_localization where weather_localization.country_id = weather_country_id and latitude = ?;";
            searchBy();
        }else if (longitudeChoiceBox.isSelected() && filterButton.isArmed()) {
            table.getItems().clear();
            query = "select weather_localization.country_name, weather_localization.region, weather_date, weather_localization.latitude, weather_localization.longitude," +
                    " temperature, pressure, humidity, wind_direction, wind_speed from weather_data join weather_localization where weather_localization.country_id = weather_country_id and longitude = ?;";
            searchBy();
        }else if (temperatureChoiceBox.isSelected() && filterButton.isArmed()) {
            table.getItems().clear();
            query = "select weather_localization.country_name, weather_localization.region, weather_date, weather_localization.latitude, weather_localization.longitude," +
                    " temperature, pressure, humidity, wind_direction, wind_speed from weather_data join weather_localization where weather_localization.country_id = weather_country_id and temperature = ?;";
            searchBy();
        }else if (pressureChoiceBox.isSelected() && filterButton.isArmed()) {
            table.getItems().clear();
            query = "select weather_localization.country_name, weather_localization.region, weather_date, weather_localization.latitude, weather_localization.longitude," +
                    " temperature, pressure, humidity, wind_direction, wind_speed from weather_data join weather_localization where weather_localization.country_id = weather_country_id and pressure = ?;";
            searchBy();
        }else if (humidityChoiceBox.isSelected() && filterButton.isArmed()) {
            table.getItems().clear();
            query = "select weather_localization.country_name, weather_localization.region, weather_date, weather_localization.latitude, weather_localization.longitude," +
                    " temperature, pressure, humidity, wind_direction, wind_speed from weather_data join weather_localization where weather_localization.country_id = weather_country_id and humidity = ?;";
            searchBy();
        }else if (windDirectionChoiceBox.isSelected() && filterButton.isArmed()) {
            table.getItems().clear();
            query = "select weather_localization.country_name, weather_localization.region, weather_date, weather_localization.latitude, weather_localization.longitude," +
                    " temperature, pressure, humidity, wind_direction, wind_speed from weather_data join weather_localization where weather_localization.country_id = weather_country_id and wind_direction = ?;";
            searchBy();
        }else if (windSpeedChoiceBox.isSelected() && filterButton.isArmed()) {
            table.getItems().clear();
            query = "select weather_localization.country_name, weather_localization.region, weather_date, weather_localization.latitude, weather_localization.longitude," +
                    " temperature, pressure, humidity, wind_direction, wind_speed from weather_data join weather_localization where weather_localization.country_id = weather_country_id and wind_speed = ?;";
            searchBy();
        }else {
            createTable();
        }
    }

    private void searchBy() throws SQLException {
        PreparedStatement preparedStatement = DBConnector
                .getConnection()
                .prepareStatement(query);

        preparedStatement.setString(SEARCH_PARAMETER_INDEX, weatherSearchField.getText());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            observableList.add(new WeatherTable(resultSet.getString("country_name"), resultSet.getString("region"), resultSet.getDate("weather_Date"),
                    resultSet.getDouble("latitude"), resultSet.getDouble("longitude"), resultSet.getDouble("temperature"),
                    resultSet.getDouble("pressure"), resultSet.getString("humidity"), resultSet.getString("wind_direction"), resultSet.getDouble("wind_speed")));
        }

        wea_country.setCellValueFactory(new PropertyValueFactory<>("country"));
        wea_region.setCellValueFactory(new PropertyValueFactory<>("region"));
        wea_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        wea_latitude.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        wea_longitude.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        wea_temperature.setCellValueFactory(new PropertyValueFactory<>("temperature"));
        wea_pressure.setCellValueFactory(new PropertyValueFactory<>("pressure"));
        wea_humidity.setCellValueFactory(new PropertyValueFactory<>("humidity"));
        wea_wind_direction.setCellValueFactory(new PropertyValueFactory<>("windDirection"));
        wea_wind_speed.setCellValueFactory(new PropertyValueFactory<>("windSpeed"));

        table.setItems(observableList);

    }

    public void switchToStartScene(ActionEvent event) throws IOException {
        fxmlLoader = FXMLLoader.load(getClass().getResource("StartScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.show();
    }

    public void exit(ActionEvent event){
        ((Stage) (((Node)event.getSource()).getScene().getWindow())).close();
    }


}
