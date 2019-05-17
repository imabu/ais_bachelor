package ru.bmstu.view.modals;

import javafx.scene.control.Alert;

public class AlertVista {
    public static void throwAlertError(String content) {
        createAlertVista(content, Alert.AlertType.ERROR, "Ошибка");
    }

    public static void throwAlertInfo(String content) {
        createAlertVista(content, Alert.AlertType.INFORMATION, "Информация");
    }

    private static void createAlertVista(String content, Alert.AlertType type, String title) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
