module ma.ensat.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;


    opens ma.ensat.javafx.Controller to javafx.fxml;
    exports ma.ensat.javafx;
}