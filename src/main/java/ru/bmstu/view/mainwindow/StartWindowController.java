package ru.bmstu.view.mainwindow;

import javafx.fxml.FXML;
import ru.bmstu.VistaNavigator;

public class StartWindowController {

    @FXML
    private void loadFileHandler() {
        VistaNavigator.loadVista(VistaNavigator.LOAD_FILE);
    }

    @FXML
    private void lookUpHandler() {
        VistaNavigator.loadVista(VistaNavigator.LOOK_UP_WINDOW);
    }
}
