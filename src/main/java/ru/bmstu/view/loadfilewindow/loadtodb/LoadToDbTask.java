package ru.bmstu.view.loadfilewindow.loadtodb;

import javafx.concurrent.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.bmstu.database.ConnectionUtil;
import ru.bmstu.database.FileMetadataHelper;
import ru.bmstu.parsingexcel.MetadataExcelSheet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class LoadToDbTask extends Task {
    private List<MetadataExcelSheet> sheetsMeta;
    private StringBuilder logMessage = new StringBuilder();
    //TODO: вынести в общий мехнизм для Tasks
    private int currentProgressPoint = -1;
    private int maxProgressPoint;

    private Logger logger = LogManager.getLogger(getClass().getName());

    public LoadToDbTask(List<MetadataExcelSheet> sheetsMeta) {
        this.sheetsMeta = sheetsMeta;
    }

    private void loadOneSheet(MetadataExcelSheet sheet) throws SQLException, LoadToDbException {
        logger.debug("Load to DB  file: " + sheet.getFileName() + ", sheet: " + sheet.getSheetName());
        String stgTableNm = FileMetadataHelper.getStgTableNM(sheet.getFileName(), sheet.getSheetName());
        Connection connection = ConnectionUtil.getConnection();
        connection.setAutoCommit(false);
        String query = "truncate table tech_facilities_stg." + stgTableNm;
        logger.debug(query);
        try {
            connection.createStatement().executeUpdate(query);
        } catch (SQLException ex) {
            logger.error("SQLException: " + ex.getMessage());
            connection.close();
            throw ex;
        }
        List<String> rowHeaders = sheet.getRowHeaders();
        query = "insert into tech_facilities_stg." + stgTableNm + " " +
                "values (" + StringUtils.repeat("?,", rowHeaders.size() - 1) + "? )";
        logger.debug(query);
        PreparedStatement preparedStmnt;
        try {
            preparedStmnt = connection.prepareStatement(query);
        } catch (SQLException ex) {
            logger.error("SQLException: " + ex.getMessage());
            connection.close();
            throw ex;
        }
        List<String> metaColumnDatatype = sheet.getMetaColumnDatatype();
        for (List<Object> row : sheet.getData()) {
            for (int i = 0; i < rowHeaders.size(); i += 1) {
                PrepareStatementHelper.setVariableToStatement(preparedStmnt, i + 1, metaColumnDatatype.get(i), row.get(i));
            }
            try {
                preparedStmnt.executeUpdate();
                upProgress();
            } catch (SQLException ex) {
                connection.close();
                logger.error("SQLException: " + ex.getMessage());
                throw ex;
            }
        }
        connection.commit();

        connection.close();
    }

    @Override
    protected Object call() throws Exception {
        this.maxProgressPoint = sheetsMeta.stream().mapToInt(MetadataExcelSheet::getRowNumber).reduce(Integer::sum).getAsInt();
        upProgress();
        logger.debug("Debug progress: maxProgressPoint = " + this.maxProgressPoint);
        for (MetadataExcelSheet sheet : sheetsMeta) {
            upProgress();
            loadOneSheet(sheet);
            upProgress();
            updateLogOut("Лист '" + sheet.getSheetName() + "' загружен в базу данных");
        }
        updateLogOut("Запуск процесса трансформации данных ... ");
        runETLProcess();
        updateLogOut("Процесс завершен успешно");
        return null;
    }

    private void runETLProcess() throws SQLException {
        Connection connection = ConnectionUtil.getConnection();
        connection.setAutoCommit(true);
        try {
            connection.prepareCall("{call tech_facilities.sp_etl_process_main()}").executeQuery();
            //connection.commit();
        } catch (SQLException ex) {
            updateLogOut("Сбой загрузки");
            logger.error("SQLException: " + ex.getMessage());
            connection.close();
            throw ex;
        }
        connection.close();
    }

    private void upProgress() {
        this.currentProgressPoint += 1;
        updateProgress(this.currentProgressPoint, this.maxProgressPoint);
    }

    private void updateLogOut(String message) {
        this.logMessage.append(message).append("\n");
        updateMessage(logMessage.toString());
    }


}
