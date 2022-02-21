package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoImpl implements Dao{

    //view
    private Stage stage;
    private Scene scene;
    private Parent fxmlLoader;
    private String query;

    //Tableview and data

    //LocationScene
    @FXML
    public TextField latitudeAddField;
    @FXML
    public TextField longitudeAddField;
    @FXML
    public TextField regionAddField;
    @FXML
    public TextField countryAddField;
    @FXML
    public Button addButton;

    //LocationShowScene
    @FXML
    public TextField locationSearchField;
    @FXML
    public CheckBox latitudeChoiceBox;
    @FXML
    public CheckBox longitudeChoiceBox;
    @FXML
    public CheckBox regionChoiceBox;
    @FXML
    public CheckBox countryChoiceBox;
    @FXML
    public Button filterButton;
    @FXML
    public TableView<LocationTable> table;
    @FXML
    public TableColumn<LocationTable, Integer> loc_id;
    @FXML
    public TableColumn<LocationTable, Double> loc_latitude;
    @FXML
    public TableColumn<LocationTable, Double> loc_longitude;
    @FXML
    public TableColumn<LocationTable, String> loc_region;
    @FXML
    public TableColumn<LocationTable, String> loc_country;

    //DBConnector
    DBConnector dbConnector = new DBConnector();
    ObservableList<LocationTable> observableList = FXCollections.observableArrayList();

    public static final int SEARCH_LATITUDE_PARAMETER_INDEX = 1;
    public static final int LONGITUDE_PARAMETER_INDEX = 2;
    public static final int REGION_PARAMETER_INDEX = 3;
    public static final int COUNTRY_PARAMETER_INDEX = 4;


    @Override
    public void checkChoiceBox(ActionEvent event) throws SQLException {
        if (latitudeChoiceBox.isSelected() && filterButton.isArmed()) {
            table.getItems().clear();
            query = "Select * from weather.weather_localization where latitude = ?;";
            searchBy();

        } else if (longitudeChoiceBox.isSelected() && filterButton.isArmed()) {
            table.getItems().clear();
            query = "Select * from weather.weather_localization where longitude = ?;";
            searchBy();
        } else if (regionChoiceBox.isSelected() && filterButton.isArmed()) {
            table.getItems().clear();
            query = "Select * from weather.weather_localization where region = ?;";
            searchBy();
        } else if (countryChoiceBox.isSelected() && filterButton.isArmed()) {
            table.getItems().clear();
            query = "Select * from weather.weather_localization where country_name = ?;";
            searchBy();
        }
    }

    @Override
    public void createTable() {
            table.getItems().clear();
            query = "Select * from weather.weather_localization;";

            try {
                Statement statement = dbConnector.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    //different method
                    observableList.add(new LocationTable(resultSet.getInt("country_id"), resultSet.getDouble("latitude"),
                            resultSet.getDouble("longitude"), resultSet.getString("region"), resultSet.getString("country_name")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //different method create dynamically based on need
            loc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            loc_latitude.setCellValueFactory(new PropertyValueFactory<>("latitude"));
            loc_longitude.setCellValueFactory(new PropertyValueFactory<>("longitude"));
            loc_region.setCellValueFactory(new PropertyValueFactory<>("region"));
            loc_country.setCellValueFactory(new PropertyValueFactory<>("country"));

            table.setItems(observableList);
    }

    @Override
    public void searchBy() throws SQLException {
        PreparedStatement preparedStatement = DBConnector
                .getConnection()
                .prepareStatement(query);

        preparedStatement.setString(SEARCH_LATITUDE_PARAMETER_INDEX, locationSearchField.getText());

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            observableList.add(new LocationTable(resultSet.getInt("country_id"), resultSet.getDouble("latitude"),
                    resultSet.getDouble("longitude"), resultSet.getString("region"), resultSet.getString("country_name")));
        }

    }

    @Override
    public void addNew(ActionEvent event) throws SQLException {
        query = "INSERT INTO weather.weather_localization (latitude, longitude, region, country_name) VALUES (?, ?, ?, ?);";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBConnector
                    .getConnection()
                    .prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        preparedStatement.setString(SEARCH_LATITUDE_PARAMETER_INDEX, latitudeAddField.getText());
        preparedStatement.setString(LONGITUDE_PARAMETER_INDEX, longitudeAddField.getText());
        preparedStatement.setString(REGION_PARAMETER_INDEX, regionAddField.getText());
        preparedStatement.setString(COUNTRY_PARAMETER_INDEX, countryAddField.getText());

        preparedStatement.executeUpdate();
        createTable();
    }


    @Override
    public void clearField(MouseEvent event) {
        locationSearchField.setText("");
    }

    @Override
    public void switchToStartScene(ActionEvent event) throws IOException {
        fxmlLoader = FXMLLoader.load(getClass().getResource("StartScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void switchToLocationScene(ActionEvent event) throws IOException {
        fxmlLoader = FXMLLoader.load(getClass().getResource("LocationScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void switchToLocationShowScene(ActionEvent event) throws IOException {
        fxmlLoader  = FXMLLoader.load(getClass().getResource("LocationShowScene.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void switchToWeatherScene(ActionEvent event) throws IOException {
        fxmlLoader  = FXMLLoader.load(getClass().getResource("WeatherScene.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void exit(ActionEvent event) {
        ((Stage) (((Node)event.getSource()).getScene().getWindow())).close();
    }
}
