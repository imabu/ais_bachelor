package ru.bmstu.parsingexcel;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MetadataExcelSheet {
    private List<String> metaColumnDatatype;
    private List<List<Object>> data = new ArrayList<>();
    private List<String> rowHeaders;
    private int rowNumber;
    private String filepath;
    private String sheetName;


    public MetadataExcelSheet(String filepath, List<String> metaColumnDatatype, String sheetName) {
        this.filepath = filepath;
        this.metaColumnDatatype = metaColumnDatatype;
        this.sheetName = sheetName;
    }

    public List<String> getMetaColumnDatatype() {
        return metaColumnDatatype;
    }

    public List<List<Object>> getData() {
        return data;
    }

    public void setData(List<List<Object>> data) {
        this.data = data;
    }

    public List<String> getRowHeaders() {
        return rowHeaders;
    }

    public void setRowHeaders(List<String> rowHeaders) {
        this.rowHeaders = rowHeaders;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getFilepath() {
        return filepath;
    }

    public String getSheetName() {
        return sheetName;
    }

    public String getFileName() {
        return Paths.get(this.filepath).getFileName().toString();
    }
}
