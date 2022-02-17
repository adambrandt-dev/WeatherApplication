module WeatherApplication {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;



    exports application;

    opens application;
}