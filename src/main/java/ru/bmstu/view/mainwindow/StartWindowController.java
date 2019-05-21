package ru.bmstu.view.mainwindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ru.bmstu.VistaNavigator;

public class StartWindowController {
    @FXML
    private void loadFileHandler(ActionEvent event) {
        VistaNavigator.loadVista(VistaNavigator.LOAD_FILE);
    }

    @FXML
    private void lookUpHandler(ActionEvent event) {
        VistaNavigator.loadVista(VistaNavigator.LOOK_UP_WINDOW);
    }

    @FXML
    void reportsHandler(ActionEvent event) {
        VistaNavigator.loadVista(VistaNavigator.SELECT_REPORT);
    }


}
