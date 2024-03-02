module io.github.vcvitaly.mazebank {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens io.github.vcvitaly.mazebank to javafx.fxml;
    exports io.github.vcvitaly.mazebank;
    exports io.github.vcvitaly.mazebank.controller;
    exports io.github.vcvitaly.mazebank.controller.admin;
    exports io.github.vcvitaly.mazebank.controller.client;
    exports io.github.vcvitaly.mazebank.model;
    exports io.github.vcvitaly.mazebank.view;
}