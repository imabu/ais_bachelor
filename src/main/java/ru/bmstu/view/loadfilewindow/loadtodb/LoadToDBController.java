package ru.bmstu.view.loadfilewindow.loadtodb;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.bmstu.VistaNavigator;
import ru.bmstu.parsingexcel.MetadataExcelSheet;
import ru.bmstu.view.loadfilewindow.LoadFileContextWrapper;

import java.util.List;

public class LoadToDBController {
    @FXML
    private JFXButton returnMainMenuButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private TextArea textAreaLog;

    private List<MetadataExcelSheet> sheetsMeta;

    private LoadToDbTask task;
    private Logger logger = LogManager.getLogger(getClass().getName());

    @FXML
    private void initialize() {
        this.sheetsMeta = LoadFileContextWrapper.getExcelMeta();
        runLoadToDB();
    }

    @FXML
    private void returnMainMenuHandler(ActionEvent event) {
        LoadFileContextWrapper.clearContext();
        VistaNavigator.loadVista(VistaNavigator.START_WINDOW);
    }

    @FXML
    private void cancelHandler(ActionEvent event) {
        this.task.cancel();
    }

    private void runLoadToDB() {
        this.task = new LoadToDbTask(this.sheetsMeta);
        textAreaLog.textProperty().bind(this.task.messageProperty());
        progressBar.progressProperty().bind(this.task.progressProperty());
        this.task.setOnSucceeded(event -> cancelButton.setDisable(true));

        new Thread(this.task).start();
    }

}
