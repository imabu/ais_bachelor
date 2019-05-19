package ru.bmstu.database.models;

import java.util.List;

public class MetadataTable {
    private String tableName;
    private String tableNameRUS;
    private List<String> columnNamesRUS;
    private List<String> columnNames;

    public MetadataTable(String tableName) {
        this.tableName = tableName;
    }

    public MetadataTable() {
    }

    public String getTableName() {
        return tableName;
    }

    public String getTableNameRUS() {
        return tableNameRUS;
    }

    public void setTableNameRUS(String tableNameRUS) {
        this.tableNameRUS = tableNameRUS;
    }

    public List<String> getColumnNamesRUS() {
        return columnNamesRUS;
    }

    public void setColumnNamesRUS(List<String> columnNamesRUS) {
        this.columnNamesRUS = columnNamesRUS;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }
}
