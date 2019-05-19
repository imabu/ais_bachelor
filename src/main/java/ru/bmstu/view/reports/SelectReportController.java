package ru.bmstu.view.reports;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.bmstu.VistaNavigator;
import ru.bmstu.database.SelectTableHelper;
import ru.bmstu.database.models.MetadataTable;
import ru.bmstu.database.models.ReportMetadata;
import ru.bmstu.parsingexcel.MetadataExcelSheet;
import ru.bmstu.parsingexcel.MetadataExcelSheetConstructor;
import ru.bmstu.parsingexcel.WritterToExcel;
import ru.bmstu.view.modals.AlertVista;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectReportController {

    @FXML
    private JFXButton saveToExcelButton;

    @FXML
    private JFXTextArea descriptionArea;

    @FXML
    private JFXComboBox<String> selectReport;

    private Logger logger = LogManager.getLogger(getClass().getName());
    private Map<String, ReportMetadata> reportsMetadataMap;

    @FXML
    private void initialize() throws SQLException {
        this.saveToExcelButton.setDisable(true);
        setReportsNames();
    }

    private void setReportsNames() throws SQLException {
        buildMapReportMetadata(SelectTableHelper.getMetadataReports());
        this.selectReport.getItems().addAll(this.reportsMetadataMap.keySet());
    }

    private void buildMapReportMetadata(List<ReportMetadata> list) {
        this.reportsMetadataMap = new HashMap<>();
        for (ReportMetadata meta : list) {
            this.reportsMetadataMap.put(meta.getReportName(), meta);
        }
    }

    @FXML
    void getReport(ActionEvent event) {
        this.descriptionArea.setText(getSelectedReportMeta().getDescription());
        this.saveToExcelButton.setDisable(false);
    }

    private ReportMetadata getSelectedReportMeta() {
        String selectedReport = this.selectReport.getValue();
        return this.reportsMetadataMap.get(selectedReport);
    }


    @FXML
    void saveToExcel(ActionEvent event) throws IOException, SQLException {
        File fileOut = getFileToSave();
        if (fileOut != null) {
            logger.debug("Get data from table " + getSelectedReportMeta().getSource());
            MetadataTable metaTable = SelectTableHelper.getMetadataTableFromMySQLMeta(getSelectedReportMeta().getSource());
            logger.debug(metaTable.getColumnNamesRUS());
            logger.debug(metaTable.getColumnNames());
            List<List<Object>> data = SelectTableHelper.getDataFromTable(metaTable);
            MetadataExcelSheet metaSheet = MetadataExcelSheetConstructor.get(data, metaTable.getColumnNamesRUS());
            new WritterToExcel(metaSheet).write(fileOut);
            logger.info("Data was saved in " + fileOut.getAbsolutePath());
            AlertVista.throwAlertInfo("Сохранено в " + fileOut.getAbsolutePath());
        }
    }

    private File getFileToSave() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String initFileName = getSelectedReportMeta().getReportName() + "_" + dateFormat.format(new Date());
        fileChooser.setInitialFileName(initFileName + ".xlsx");
        return fileChooser.showSaveDialog(VistaNavigator.getPrimaryStage());
    }

    @FXML
    public void returnMainMenuHandler(ActionEvent ev) {
        VistaNavigator.loadVista(VistaNavigator.START_WINDOW);
    }

}
