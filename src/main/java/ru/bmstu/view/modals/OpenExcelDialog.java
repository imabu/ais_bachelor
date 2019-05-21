package ru.bmstu.view.modals;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class OpenExcelDialog {
    public static SELECTED_BUTTON show(String filepath) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Открытие файла");
        alert.setHeaderText("Файл " + filepath + " сохранен.\nХотите открыть его?");
        ButtonType cancel = new ButtonType("Нет");
        ButtonType ok = new ButtonType("Да");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ok, cancel);
        Optional<ButtonType> option = alert.showAndWait();
        ButtonType selected = option.get();
        if (selected == ok) {
            return SELECTED_BUTTON.BUTTON_OK;
        } else {
            return SELECTED_BUTTON.BUTTON_CANCEL;
        }
    }

    public enum SELECTED_BUTTON {BUTTON_OK, BUTTON_CANCEL}
}
