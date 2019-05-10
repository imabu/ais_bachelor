package ru.bmstu.parsingexcel;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.bmstu.database.ConnectionUtil;
import ru.bmstu.database.FileMetadataHelper;
import ru.bmstu.view.AlertVista;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MetadataExcelSheetConstructor {
    private static Logger logger = LogManager.getLogger(MetadataExcelSheetConstructor.class.getName());

    public static List<MetadataExcelSheet> get(Path filePath) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();
        String filename = filePath.getFileName().toString();
        List<MetadataExcelSheet> sheetsMeta = new ArrayList<>();
        List<String> sheetsList = null;
        try {
            sheetsList = FileMetadataHelper.getSheetsNames(filename);
        } catch (SQLException e) {
            logger.error(e);
            AlertVista.throwAlert("Ошибка при работе с базой данных");
        }
        for (String sheet : sheetsList) {
            List<String> colMetadata = FileMetadataHelper.getColumnMetadata(filename, sheet);
            sheetsMeta.add(new MetadataExcelSheet(filePath.toString(), colMetadata, sheet));
        }
        connection.close();
        return sheetsMeta;
    }
}
