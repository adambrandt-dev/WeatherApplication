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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LocationShowSceneController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent fxmlLoader;


    @FXML
    private TextField locationSearchField;

    @FXML
    private CheckBox latitudeChoiceBox;
    @FXML
    private CheckBox longitudeChoiceBox;
    @FXML
    private CheckBox regionChoiceBox;
    @FXML
    private CheckBox countryChoiceBox;
    @FXML
    private Button filterButton;


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

    public static final int SEARCH_PARAMETER_INDEX = 1;


    String query;
    ObservableList<LocationTable> observableList = FXCollections.observableArrayList();
    DBConnector dbConnector = new DBConnector();

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

    private void searchBy() throws SQLException {
        PreparedStatement preparedStatement = DBConnector
                .getConnection()
                .prepareStatement(query);

        preparedStatement.setString(SEARCH_PARAMETER_INDEX, locationSearchField.getText());

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            observableList.add(new LocationTable(resultSet.getInt("country_id"), resultSet.getDouble("latitude"),
                    resultSet.getDouble("longitude"), resultSet.getString("region"), resultSet.getString("country_name")));
        }

        loc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        loc_latitude.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        loc_longitude.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        loc_region.setCellValueFactory(new PropertyValueFactory<>("region"));
        loc_country.setCellValueFactory(new PropertyValueFactory<>("country"));
    }

    public void clearField(MouseEvent event) {
        locationSearchField.setText("");
    }


    public void switchToStartScene(ActionEvent event) throws IOException {
        fxmlLoader = FXMLLoader.load(getClass().getResource("StartScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLocationScene(ActionEvent event) throws IOException {
        fxmlLoader = FXMLLoader.load(getClass().getResource("LocationScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.show();
    }

    public void exit(ActionEvent event){
        ((Stage) (((Node)event.getSource()).getScene().getWindow())).close();
    }

}
