package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoImpl implements Dao {

    //view
    private Stage stage;
    private Scene scene;
    private Parent fxmlLoader;

    @FXML
    private CheckBox latitudeChoiceBox;
    @FXML
    private CheckBox longitudeChoiceBox;
    @FXML
    private CheckBox regionChoiceBox;
    @FXML
    private CheckBox countryChoiceBox;
    @FXML
    private CheckBox dateChoiceBox;
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
    private TextField searchField;
    @FXML
    public Button filterButton;

    @FXML
    private TextField latitudeAddField;
    @FXML
    private TextField longitudeAddField;
    @FXML
    private TextField regionAddField;
    @FXML
    private TextField countryAddField;
    @FXML
    public Button addButton;


    public static final int LATITUDE_PARAMETER_INDEX = 1;
    public static final int LONGITUDE_PARAMETER_INDEX = 2;
    public static final int REGION_PARAMETER_INDEX = 3;
    public static final int COUNTRY_PARAMETER_INDEX = 4;

    //Tableview and data
    @FXML
    TableView tableView;

    private ObservableList<ObservableList> observableList = FXCollections.observableArrayList();
    private String query;

    //DBConnector
    DBConnector dbConnector = new DBConnector();
    public static final int SEARCH_PARAMETER_INDEX = 1;

    @Override
    public void checkChoiceBox(ActionEvent event) throws SQLException {
        if (latitudeChoiceBox.isSelected() && filterButton.isArmed()) {
            tableView.getItems().clear();
            query = "Select * from weather.weather_localization where latitude = ?;";
            searchBy();

        } else if (longitudeChoiceBox.isSelected() && filterButton.isArmed()) {
            tableView.getItems().clear();
            query = "Select * from weather.weather_localization where longitude = ?;";
            searchBy();
        } else if (regionChoiceBox.isSelected() && filterButton.isArmed()) {
            tableView.getItems().clear();
            query = "Select * from weather.weather_localization where region = ?;";
            searchBy();
        } else if (countryChoiceBox.isSelected() && filterButton.isArmed()) {
            tableView.getItems().clear();
            query = "Select * from weather.weather_localization where country_name = ?;";
            searchBy();
        }
    }

    @Override
    public void checkWeatherChoiceBox(ActionEvent event) throws SQLException {
        if (countryChoiceBox.isSelected() && filterButton.isArmed()) {
            tableView.getItems().clear();
            query = "select weather_localization.country_name, weather_localization.region, weather_date, weather_localization.latitude, weather_localization.longitude," +
                    " temperature, pressure, humidity, wind_direction, wind_speed from weather_data join weather_localization where weather_localization.country_id = weather_country_id and weather_localization.country_name = ?;";
            searchBy();

        } else if (regionChoiceBox.isSelected() && filterButton.isArmed()) {
            tableView.getItems().clear();
            query = "select weather_localization.country_name, weather_localization.region, weather_date, weather_localization.latitude, weather_localization.longitude," +
                    " temperature, pressure, humidity, wind_direction, wind_speed from weather_data join weather_localization where weather_localization.country_id = weather_country_id and weather_localization.region = ?;";
            searchBy();
        } else if (dateChoiceBox.isSelected() && filterButton.isArmed()) {
            tableView.getItems().clear();
            query = "select weather_localization.country_name, weather_localization.region, weather_date, weather_localization.latitude, weather_localization.longitude," +
                    " temperature, pressure, humidity, wind_direction, wind_speed from weather_data join weather_localization where weather_localization.country_id = weather_country_id and weather_date = ?;";
            searchBy();
        } else if (latitudeChoiceBox.isSelected() && filterButton.isArmed()) {
            tableView.getItems().clear();
            query = "select weather_localization.country_name, weather_localization.region, weather_date, weather_localization.latitude, weather_localization.longitude," +
                    " temperature, pressure, humidity, wind_direction, wind_speed from weather_data join weather_localization where weather_localization.country_id = weather_country_id and latitude = ?;";
            searchBy();
        }else if (longitudeChoiceBox.isSelected() && filterButton.isArmed()) {
            tableView.getItems().clear();
            query = "select weather_localization.country_name, weather_localization.region, weather_date, weather_localization.latitude, weather_localization.longitude," +
                    " temperature, pressure, humidity, wind_direction, wind_speed from weather_data join weather_localization where weather_localization.country_id = weather_country_id and longitude = ?;";
            searchBy();
        }else if (temperatureChoiceBox.isSelected() && filterButton.isArmed()) {
            tableView.getItems().clear();
            query = "select weather_localization.country_name, weather_localization.region, weather_date, weather_localization.latitude, weather_localization.longitude," +
                    " temperature, pressure, humidity, wind_direction, wind_speed from weather_data join weather_localization where weather_localization.country_id = weather_country_id and temperature = ?;";
            searchBy();
        }else if (pressureChoiceBox.isSelected() && filterButton.isArmed()) {
            tableView.getItems().clear();
            query = "select weather_localization.country_name, weather_localization.region, weather_date, weather_localization.latitude, weather_localization.longitude," +
                    " temperature, pressure, humidity, wind_direction, wind_speed from weather_data join weather_localization where weather_localization.country_id = weather_country_id and pressure = ?;";
            searchBy();
        }else if (humidityChoiceBox.isSelected() && filterButton.isArmed()) {
            tableView.getItems().clear();
            query = "select weather_localization.country_name, weather_localization.region, weather_date, weather_localization.latitude, weather_localization.longitude," +
                    " temperature, pressure, humidity, wind_direction, wind_speed from weather_data join weather_localization where weather_localization.country_id = weather_country_id and humidity = ?;";
            searchBy();
        }else if (windDirectionChoiceBox.isSelected() && filterButton.isArmed()) {
            tableView.getItems().clear();
            query = "select weather_localization.country_name, weather_localization.region, weather_date, weather_localization.latitude, weather_localization.longitude," +
                    " temperature, pressure, humidity, wind_direction, wind_speed from weather_data join weather_localization where weather_localization.country_id = weather_country_id and wind_direction = ?;";
            searchBy();
        }else if (windSpeedChoiceBox.isSelected() && filterButton.isArmed()) {
            tableView.getItems().clear();
            query = "select weather_localization.country_name, weather_localization.region, weather_date, weather_localization.latitude, weather_localization.longitude," +
                    " temperature, pressure, humidity, wind_direction, wind_speed from weather_data join weather_localization where weather_localization.country_id = weather_country_id and wind_speed = ?;";
            searchBy();
        }else {
            createTable();
        }
    }


    @Override
    public void createTable() {
        tableView.getColumns().clear();
        System.out.println("-------------------");
        System.out.println(tableView.getColumns().toString());
        System.out.println(tableView.getItems().toString());
        System.out.println("-------------------");
        String query = "Select * from weather.weather_localization;";

        try {
            Statement statement = DBConnector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableView.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");
            }

            while (resultSet.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(resultSet.getString(i));
                }
                System.out.println("Row [1] added " + row);
                observableList.add(row);
            }
            //Add data to TableView
            tableView.setItems(observableList);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    @Override
    public void createWeatherTable() {
        tableView.getColumns().clear();
        System.out.println("-------------------");
        System.out.println(tableView.getColumns().toString());
        System.out.println(tableView.getItems().toString());
        System.out.println("-------------------");
        String query = "select weather_localization.country_name, weather_localization.region, weather_date, weather_localization.latitude, weather_localization.longitude," +
                " temperature, pressure, humidity, wind_direction, wind_speed from weather_data join weather_localization where weather_localization.country_id = weather_country_id;";


        try {
            Statement statement = DBConnector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableView.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");
            }

            while (resultSet.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(resultSet.getString(i));
                }
                System.out.println("Row [1] added " + row);
                observableList.add(row);
            }
            //Add data to TableView
            tableView.setItems(observableList);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    @Override
    public void searchBy() throws SQLException {
        tableView.getColumns().clear();
        PreparedStatement preparedStatement = DBConnector
                .getConnection()
                .prepareStatement(query);

        preparedStatement.setString(SEARCH_PARAMETER_INDEX, searchField.getText());

        ResultSet resultSet = preparedStatement.executeQuery();

        for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
            //We are using non property style for making dynamic table
            final int j = i;
            TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });

            tableView.getColumns().addAll(col);
            System.out.println("Column [" + i + "] ");
        }

        while (resultSet.next()) {
            //Iterate Row
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                //Iterate Column
                row.add(resultSet.getString(i));
            }
            System.out.println("Row [1] added " + row);
            observableList.add(row);


        }
    }

    @Override
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

    @Override
    public void clearField(MouseEvent event) {
        searchField.setText("");
    }

    @Override
    public void switchToStartScene(ActionEvent event) throws IOException {
        fxmlLoader = FXMLLoader.load(getClass().getResource("StartScene.fxml"));
        stage = new Stage();
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
        fxmlLoader = FXMLLoader.load(getClass().getResource("LocationShowScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void switchToWeatherScene(ActionEvent event) throws IOException {
        fxmlLoader = FXMLLoader.load(getClass().getResource("WeatherScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void exit(ActionEvent event) {
        ((Stage) (((Node) event.getSource()).getScene().getWindow())).close();
    }
}
