package ru.bmstu.view.lookupwindow;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;
import ru.bmstu.database.SelectTableHelper;
import ru.bmstu.database.models.MetadataTable;
import ru.bmstu.parsingexcel.MetadataExcelSheet;
import ru.bmstu.parsingexcel.MetadataExcelSheetConstructor;
import ru.bmstu.view.utils.Context;
import ru.bmstu.view.utils.GeneralTableView;
import ru.bmstu.view.utils.SaveToExcel;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GeneralLookUpController {
    @FXML
    private Label nRows;

    @FXML
    private Label entityNmLabel;

    @FXML
    private JFXButton saveToExcelButton;

    @FXML
    private JFXButton getDataButton;
    @FXML
    private TableView<ObservableList<String>> tableView;
    private MetadataTable meta;


    private String tableName;
    private Logger logger = LogManager.getLogger(getClass().getName());

    @FXML
    private void initialize() {
        this.saveToExcelButton.setVisible(false);
        this.entityNmLabel.setVisible(false);
        this.nRows.setVisible(false);
        this.entityNmLabel.setText("");
        this.nRows.setText("");
    }

    private void getContext() {
        this.tableName = (String) Context.getContextObject(this.toString());
        logger.info("initialize controller for table " + this.tableName);
        Context.removeContextObject(this.toString());
    }

    @FXML
    void getData(ActionEvent event) throws SQLException {
        getContext();
        this.meta = SelectTableHelper.getMetadataTableFromCustomMeta(this.tableName);
        List<List<Object>> data = SelectTableHelper.getDataFromTable(meta);

        GeneralTableView.buildTableView(this.tableView, this.meta.getColumnNamesRUS(), data);
        TableFilter.forTableView(this.tableView).apply();
        changeHeaderView(this.meta.getTableNameRUS(), data.size());
    }

    @FXML
    void saveToExcel(ActionEvent event) throws IOException {
        File fileOut = SaveToExcel.getFileToSave(this.meta.getTableNameRUS());
        if (fileOut != null) {
            ObservableList<ObservableList<String>> observeItems = tableView.getItems();
            List<List<Object>> data = new ArrayList<>();
            for (ObservableList<String> observeRow : observeItems) {
                data.add(new ArrayList<>(observeRow));
            }
            MetadataExcelSheet metaSheet = MetadataExcelSheetConstructor.get(data, meta.getColumnNamesRUS());
            SaveToExcel.save(metaSheet, fileOut);
        }
    }

    private void changeHeaderView(String header, int nrow) {
        this.getDataButton.setVisible(false);
        this.saveToExcelButton.setVisible(true);
        this.entityNmLabel.setVisible(true);
        this.entityNmLabel.setText(header);
        this.nRows.setVisible(true);
        this.nRows.setText(nrow == 0 ? "Нет данных" : "Количество строк: " + nrow);
    }


}
