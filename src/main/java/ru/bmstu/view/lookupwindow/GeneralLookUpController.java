package ru.bmstu.view.lookupwindow;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;
import ru.bmstu.database.MetadataTable;
import ru.bmstu.database.SelectTableHelper;
import ru.bmstu.view.utils.Context;
import ru.bmstu.view.utils.GeneralTableView;

import java.sql.SQLException;
import java.util.List;

public class GeneralLookUpController {
    @FXML
    private JFXButton getDataButton;
    @FXML
    private TableView<ObservableList<String>> tableView;

    private String tableName;
    private Logger logger = LogManager.getLogger(getClass().getName());

    private void setContext() {
        this.tableName = (String) Context.getContextObject(this.toString());
        logger.info("initialize controller for table " + this.tableName);
        Context.removeContextObject(this.toString());
    }

    @FXML
    void getData(ActionEvent event) throws SQLException {
        setContext();
        MetadataTable meta = SelectTableHelper.getMetadataTable(this.tableName);
        List<List<Object>> data = SelectTableHelper.getDataFromTable(meta);

        GeneralTableView.buildTableView(this.tableView, meta.getColumnNamesRUS(), data);
        TableFilter.forTableView(this.tableView).apply();
    }
}
