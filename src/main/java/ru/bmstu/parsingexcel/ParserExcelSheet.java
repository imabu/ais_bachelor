package ru.bmstu.parsingexcel;

import org.apache.commons.lang3.StringUtils;
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
        List<List<Object>> sheetData = new ArrayList<>();
        List<String> rowHeaders = new ArrayList<>();
        int readRows = 0;
        try {
            FileInputStream excelFile = new FileInputStream(new File(excel.getFilepath()));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheet(excel.getSheetName());
            int COLUMN_CN = 0;
            for (Row currentRow : datatypeSheet) {
                if (!checkIfRowIsEmpty(currentRow)) {
                    readRows += 1;
                    if (readRows == 1) {
                        for (Cell currentCell : currentRow) {
                            rowHeaders.add(currentCell.getStringCellValue());
                            COLUMN_CN = rowHeaders.size();
                        }
                    } else {
                        List<Object> rowData = new ArrayList<>();
                        for (int i = 0; i < COLUMN_CN; i += 1) {
                            Cell currentCell = currentRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            logger.debug("Row #" + readRows + " " + currentCell.getCellType().name());
                            rowData.add(extractDataFromCell(currentCell));
                        }

                        sheetData.add(rowData);
                    }

                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e);
        }
        excel.setRowNumber(readRows);
        excel.setRowHeaders(rowHeaders);
        excel.setData(sheetData);
        logger.info("Read " + readRows + " rows");
        return sheetData;
    }

    private Object extractDataFromCell(Cell currentCell) {
        Object data = "";
        switch (currentCell.getCellType()) {
            case STRING:
                data = currentCell.getStringCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(currentCell)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    data = dateFormat.format(currentCell.getDateCellValue());
                } else {
                    data = currentCell.getNumericCellValue();
                }
                break;
            case BLANK:
                data = "";
                break;
            case FORMULA:
                switch (currentCell.getCachedFormulaResultType()) {
                    case NUMERIC:
                        data = currentCell.getNumericCellValue();
                        break;
                    case STRING:
                        data = currentCell.getRichStringCellValue().getString();
                        break;
                }
        }
        return data;
    }

    private boolean checkIfRowIsEmpty(Row row) {
        if (row == null) {
            return true;
        }
        if (row.getLastCellNum() <= 0) {
            return true;
        }
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK && StringUtils.isNotBlank(cell.toString())) {
                return false;
            }
        }
        return true;
    }

}


