package ru.bmstu.parsingexcel;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class WritterToExcel {
    private MetadataExcelSheet excel;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private CellStyle headerStyle;
    private Logger logger = LogManager.getLogger(getClass().getName());


    public WritterToExcel(MetadataExcelSheet excel) {
        this.excel = excel;
        this.workbook = new XSSFWorkbook();
        this.sheet = this.workbook.createSheet();
        initHeaderStyle();
    }

    public void write(File file) throws IOException {
        buildXSSFWorkbook();
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            this.workbook.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    private void buildXSSFWorkbook() {
        List<String> headers = excel.getRowHeaders();
        XSSFRow header = this.sheet.createRow(0);

        for (int i = 0; i < headers.size(); i += 1) {
            header.createCell(i).setCellValue(headers.get(i));
            sheet.autoSizeColumn(i);
        }
        header.setRowStyle(headerStyle);

        List<List<Object>> data = excel.getData();
        for (int i = 0; i < data.size(); i += 1) {
            XSSFRow row = this.sheet.createRow(i + 1);
            List<Object> rowData = data.get(i);
            for (int j = 0; j < rowData.size(); j += 1) {
                row.createCell(j).setCellValue((String) rowData.get(j));
            }
        }
    }

    private void initHeaderStyle() {
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        boldFont.setFontHeightInPoints((short) 12);
        headerStyle.setFont(boldFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setLocked(true);
        headerStyle.setWrapText(true);
    }



}
