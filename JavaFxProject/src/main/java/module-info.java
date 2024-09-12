module pong.assignment.javafxproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens pong.assignment.javafxproject to javafx.fxml;

    opens pong.assignment.javafxproject.view to javafx.graphics;

    exports pong.assignment.javafxproject.model;
    exports pong.assignment.javafxproject.controller;
    exports pong.assignment.javafxproject.view;
}