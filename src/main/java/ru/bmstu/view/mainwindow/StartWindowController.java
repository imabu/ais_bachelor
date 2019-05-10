package ru.bmstu.view.mainwindow;

import javafx.fxml.FXML;
import ru.bmstu.VistaNavigator;

public class StartWindowController {

    @FXML
    private void loadFileHandler(){
        VistaNavigator.loadVista(VistaNavigator.LOAD_FILE);
    }
}
