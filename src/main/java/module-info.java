module rasel.aliam {
    requires javafx.controls;
    requires javafx.fxml;


    opens rasel.aliam to javafx.fxml;
    exports rasel.aliam;
}