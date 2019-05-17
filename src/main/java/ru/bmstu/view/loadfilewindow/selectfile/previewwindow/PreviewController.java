package ru.bmstu.view.loadfilewindow.selectfile.previewwindow;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.bmstu.parsingexcel.MetadataExcelSheet;
import ru.bmstu.view.loadfilewindow.LoadFileContextWrapper;
import ru.bmstu.view.utils.GeneralTableView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PreviewController {
    @FXML
    private TableView<ObservableList<String>> tableView;

    @FXML
    private ComboBox<String> boxForSheetsNames;

    private Map<String, MetadataExcelSheet> excelSheetsMap;
    private Logger logger = LogManager.getLogger(getClass().getName());

    @FXML
    private void initialize() {
        configureExcelData(LoadFileContextWrapper.getExcelMeta());
    }

    public void configureExcelData(List<MetadataExcelSheet> excelSheets) {
        this.excelSheetsMap = new HashMap<>();
        for (MetadataExcelSheet sheet : excelSheets) {
            this.excelSheetsMap.put(sheet.getSheetName(), sheet);
        }
        List<String> sheetNames = excelSheets.stream().map(MetadataExcelSheet::getSheetName).collect(Collectors.toList());
        setSheetsNames(sheetNames);
    }

    private void setSheetsNames(List<String> headers) {
        boxForSheetsNames.getItems().addAll(headers);
    }

    @FXML
    private void selectSheet(ActionEvent ev) {
        String selectedItem = boxForSheetsNames.getSelectionModel().getSelectedItem();
        MetadataExcelSheet sheet = excelSheetsMap.get(selectedItem);
        GeneralTableView.buildTableView(this.tableView, sheet.getRowHeaders(), sheet.getData());
        logger.info("Update table view with sheet " + sheet.getSheetName());
    }


}
