package ru.bmstu;

import ru.bmstu.database.FileMetadataHelper;
import ru.bmstu.parsingexcel.MetadataExcelSheet;
import ru.bmstu.parsingexcel.ParserExcelSheet;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = new Date();
        System.setProperty("logdir", "C:\\Users\\Karina.Kuchaeva\\Documents\\PersonalProjects\\etl_core\\tmp");
        System.setProperty("logfilename", "main_" + dateFormat.format(date));
        System.setProperty("tech_user", "root");
        System.setProperty("tech_user_password", "admin");
        System.setProperty("mysql_url", "jdbc:mysql://localhost:3306/default");

        String excelFilenameSample = "C:\\Users\\Karina.Kuchaeva\\Documents\\PersonalProjects\\etl_core\\resources\\dlr\\me\\sample.xlsx";
        String excelSheetNameSample = "test";
        List<String> metaColumnSample = FileMetadataHelper.getColumnMetadata("sample.xlsx", excelSheetNameSample);
        System.out.println(metaColumnSample);

        MetadataExcelSheet excelMetaSample = new MetadataExcelSheet(excelFilenameSample, metaColumnSample, excelSheetNameSample);
        ParserExcelSheet parseExcelTest = new ParserExcelSheet(excelMetaSample);
        List<List<Object>> data = parseExcelTest.parse();
        System.out.println(data);

    }
}
