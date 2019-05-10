package ru.bmstu.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class MainController {
    @FXML
    private BorderPane vistaHolder;

    public void setVista(Node node) {
        vistaHolder.setCenter(node);
    }

    public BorderPane getVistaHolder() {
        return vistaHolder;
    }
}
