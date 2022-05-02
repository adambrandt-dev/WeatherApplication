package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    final String url = "jdbc:mysql://localhost:3306/weather?serverTime=CET";
    final String user = "root";
    final String password = "Stella4LIFE1@3";
    final String query = "SELECT * FROM weather.weather_localization;";

    private DBConnector instance;
    private static Connection connection;

    public DBConnector() {
        try {
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected");

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public DBConnector getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConnector();
        } else if (instance.getConnection().isClosed()) {
            instance = new DBConnector();
        }
        return instance;
    }

    public static Connection getConnection() {
        return connection;
    }
}

