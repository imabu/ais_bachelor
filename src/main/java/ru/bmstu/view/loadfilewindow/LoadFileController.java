package ru.bmstu.view.loadfilewindow;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.bmstu.VistaNavigator;
import ru.bmstu.parsingexcel.MetadataExcelSheet;
import ru.bmstu.view.loadfilewindow.previewwindow.PreviewController;

import java.nio.file.Path;
import java.util.List;

public class LoadFileController {

    List<MetadataExcelSheet> sheetsMeta;
    @FXML
    private TextField filePathArea;
    @FXML
    private JFXButton selectFileButton;
    @FXML
    private TextArea testAreaLog;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private JFXButton returnButton;
    @FXML
    private JFXButton continueButton;
    @FXML
    private JFXButton previewButton;
    private Logger logger = LogManager.getLogger(getClass().getName());

    public LoadFileController() {
    }

    @FXML
    private void initialize() {
        progressBar.setVisible(false);
        testAreaLog.setVisible(false);
        continueButton.setDisable(true);
        previewButton.setVisible(false);
    }

    private void guiSettingsSelectFileHandler() {
        progressBar.setVisible(true);
        testAreaLog.setVisible(true);
    }

    @FXML
    public void selectFileHandler(ActionEvent ev) {
        Path selectedFile = LoadFileHelper.getFile();
        if (selectedFile != null) {
            filePathArea.setText(selectedFile.toString());
            filePathArea.setDisable(true);

            guiSettingsSelectFileHandler();

            runTaskLoad(selectedFile);
        }
    }

    private void runTaskLoad(Path selectedFile) {
        LoadFileTask loadTask = LoadFileHelper.getLoadTask(selectedFile);
        progressBar.progressProperty().bind(loadTask.progressProperty());
        testAreaLog.textProperty().bind(loadTask.messageProperty());
        loadTask.setOnSucceeded(event -> {
            if (loadTask.getStatus()) {
                continueButton.setDisable(false);
                previewButton.setVisible(true);
                this.sheetsMeta = loadTask.getSheetsMeta();
            }
        });
        new Thread(loadTask).start();
    }

    @FXML
    public void previewHandler(ActionEvent ev) {
        PreviewController previewController = (PreviewController) VistaNavigator.openNewVista(VistaNavigator.PREVIEW_FILE);
        previewController.setExcelSheets(this.sheetsMeta);
    }


}
