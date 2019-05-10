package ru.bmstu.parsingexcel;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ParserExcelSheet {
    private MetadataExcelSheet excel;
    private Logger logger = LogManager.getLogger(ParserExcelSheet.class.getName());

    public ParserExcelSheet(MetadataExcelSheet excel) {
        this.excel = excel;
    }

    public List<List<Object>> parse() {
        List<List<Object>> sheetData = new ArrayList<List<Object>>();
        List<String> rowHeaders = new ArrayList<String>();
        int readRows = 0;
        try {
            FileInputStream excelFile = new FileInputStream(new File(excel.getFilepath()));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheet(excel.getSheetName());
            for (Row currentRow : datatypeSheet) {
                readRows += 1;
                if (readRows == 1) {
                    for (Cell currentCell : currentRow) {
                        rowHeaders.add(currentCell.getStringCellValue());
                    }
                } else {
                    List<Object> rowData = new ArrayList<Object>();
                    for (Cell currentCell : currentRow) {
                        switch (currentCell.getCellType()) {
                            case STRING:
                                rowData.add(currentCell.getStringCellValue());
                                break;
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(currentCell)) {
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                    rowData.add(dateFormat.format(currentCell.getDateCellValue()));
                                } else {
                                    rowData.add(currentCell.getNumericCellValue());
                                }
                                break;
                            case FORMULA:
                                break;
                            case BLANK:
                                break;
                            case BOOLEAN:
                                break;
                            case ERROR:
                                break;
                        }
                    }
                    sheetData.add(rowData);
                }

            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        excel.setRowNumber(readRows);
        excel.setRowHeaders(rowHeaders);
        excel.setData(sheetData);
        logger.info("Read " + readRows + " rows");
        return sheetData;
    }

}


