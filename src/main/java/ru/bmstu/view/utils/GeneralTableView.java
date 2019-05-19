package ru.bmstu.view.utils;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class GeneralTableView {
    private static Logger logger = LogManager.getLogger(GeneralTableView.class.getName());

    public static void buildTableView(TableView<ObservableList<String>> tableView,
                                      List<String> columnNames,
                                      List<List<Object>> data) {
        logger.debug("Column name " + columnNames);
        tableView.getColumns().clear();
        for (int i = 0; i < columnNames.size(); i++) {
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                    columnNames.get(i)
            );
            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
            );
            tableView.getColumns().add(column);
        }

        tableView.getItems().clear();
        for (List<Object> datum : data) {
            List<String> dataStr = datum.stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
            tableView.getItems().add(
                    FXCollections.observableArrayList(dataStr)
            );
        }
        tableView.refresh();
    }
}
