module io.github.vcvitaly.mazebank {
    requires javafx.controls;
    requires javafx.fxml;


    opens io.github.vcvitaly.mazebank to javafx.fxml;
    exports io.github.vcvitaly.mazebank;
}