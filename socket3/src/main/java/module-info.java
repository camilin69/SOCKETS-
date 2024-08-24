module co.edu.uptc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens co.edu.uptc to javafx.fxml;
    exports co.edu.uptc;
}
