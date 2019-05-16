package ru.bmstu.view.loadfilewindow.selectfile.previewwindow;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.bmstu.parsingexcel.MetadataExcelSheet;
import ru.bmstu.view.Context;
import ru.bmstu.view.loadfilewindow.LoadFileContextWrapper;

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
        buildTableView(excelSheetsMap.get(selectedItem));
    }

    private void buildTableView(MetadataExcelSheet sheet) {
        TableView<ObservableList<String>> tableView = new TableView<>();
        List<String> columnNames = sheet.getRowHeaders();
        logger.info("Column name " + columnNames);
        this.tableView.getColumns().clear();
        for (int i = 0; i < columnNames.size(); i++) {
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                    columnNames.get(i)
            );
            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
            );
            this.tableView.getColumns().add(column);
        }
        List<List<Object>> data = sheet.getData();
        this.tableView.getItems().clear();
        for (List<Object> datum : data) {
            List<String> dataStr = datum.stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
            logger.info(dataStr);
            this.tableView.getItems().add(
                    FXCollections.observableArrayList(dataStr)
            );
        }
        logger.info("Update table view with sheet " + sheet.getSheetName());
        this.tableView.refresh();
    }


}
