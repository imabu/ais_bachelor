package ru.bmstu.view.loadfilewindow.selectfile;

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
import ru.bmstu.view.loadfilewindow.LoadFileContextWrapper;
import ru.bmstu.view.loadfilewindow.selectfile.previewwindow.PreviewController;

import java.nio.file.Path;
import java.util.List;

public class SelectFileController {

    private List<MetadataExcelSheet> sheetsMeta;
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

    public SelectFileController() {
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
        Path selectedFile = LoadFileToAppHelper.getFile();
        if (selectedFile != null) {
            filePathArea.setText(selectedFile.toString());
            filePathArea.setDisable(true);

            guiSettingsSelectFileHandler();

            runTaskLoad(selectedFile);
        }
    }

    private void runTaskLoad(Path selectedFile) {
        LoadFileToAppTask loadTask = LoadFileToAppHelper.getLoadTask(selectedFile);
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
        LoadFileContextWrapper.addExcelMeta(sheetsMeta);
        PreviewController previewController = (PreviewController) VistaNavigator.openNewVista(VistaNavigator.PREVIEW_FILE);
    }

    @FXML
    public void continueHandler(ActionEvent ev) {
        LoadFileContextWrapper.addExcelMeta(sheetsMeta);
        VistaNavigator.loadVista(VistaNavigator.LOAD_TO_DB);
    }

    @FXML
    public void returnHandler(ActionEvent ev) {
        VistaNavigator.loadVista(VistaNavigator.START_WINDOW);
    }

}
