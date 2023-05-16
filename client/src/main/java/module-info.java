module vnua.client {
    requires javafx.controls;
    requires javafx.fxml;

    opens vnua.client to javafx.fxml;
    exports vnua.client;
}
