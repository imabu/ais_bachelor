package ru.bmstu.view.alerts;

import javafx.scene.control.Alert;

public class AlertVista {
    public static void throwAlert(String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setContentText(content);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
