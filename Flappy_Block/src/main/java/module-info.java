module com.example.flappy_block {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.flappy_block to javafx.fxml;
    exports com.example.flappy_block;
}