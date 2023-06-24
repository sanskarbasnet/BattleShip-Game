module com.example.battleship {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.battleship to javafx.fxml;
    exports com.example.battleship;
}