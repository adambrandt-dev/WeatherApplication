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

public class DaoImpl implements Dao{

    //view
     Stage stage;
     Scene scene;
     Parent fxmlLoader;

    @FXML
    private CheckBox latitudeChoiceBox;
    @FXML
    private CheckBox longitudeChoiceBox;
    @FXML
    private CheckBox regionChoiceBox;
    @FXML
    private CheckBox countryChoiceBox;
    @FXML
    private TextField locationSearchField;
    @FXML
    public Button filterButton;

    //Tableview and data
    @FXML
    private TableView tableView;

    private ObservableList<ObservableList> observableList;
    private String query;

    //DBConnector
    DBConnector dbConnector = new DBConnector();
    public static final int SEARCH_PARAMETER_INDEX = 1;

    @Override
    public void checkChoiceBox(ActionEvent event) throws SQLException{
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
    public void createTable() {
        tableView.getItems().clear();
        observableList = FXCollections.observableArrayList();
        String query = "Select * from weather.weather_localization;";

        try {
            Statement statement = dbConnector.getConnection().createStatement();
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

//            System.out.println("-------------------");
//            for (int i = 0; i < observableList.size(); i++){
//                System.out.println(observableList.get(i));
//            }

            System.out.println("-------------------");
            System.out.println(tableView.getItems().toString());
            System.out.println("-------------------");


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    @Override
    public void searchBy() throws SQLException {
        PreparedStatement preparedStatement = DBConnector
                .getConnection()
                .prepareStatement(query);

        preparedStatement.setString(SEARCH_PARAMETER_INDEX, locationSearchField.getText());

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

//            observableList.add(row);
//            for (int i = 0; i < observableList.size(); i++){
//                System.out.println(observableList.get(i));
//            }

        }
    }

    @Override
    public void addNew() {

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
