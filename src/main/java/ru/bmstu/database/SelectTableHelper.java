package ru.bmstu.database;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.bmstu.database.models.MetadataTable;
import ru.bmstu.database.models.ReportMetadata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectTableHelper {
    private static Logger logger = LogManager.getLogger(SelectTableHelper.class.getName());


    public static List<List<Object>> getDataFromTable(MetadataTable metadata) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();

        String query = "select " + String.join(", ", metadata.getColumnNames()) +
                " from tech_facilities." + metadata.getTableName();
        List<List<Object>> data = new ArrayList<>();
        logger.debug(query);
        try {
            ResultSet rs = connection.createStatement().executeQuery(query);
            while (rs.next()) {
                List<Object> row = new ArrayList<>();
                for (String column : metadata.getColumnNames()) {
                    String val = rs.getString(column);
                    if (rs.wasNull()) {
                        logger.debug(column + " is null");
                        row.add("");
                    } else {
                        row.add(val);
                    }
                }
                data.add(row);
            }
        } catch (SQLException ex) {
            logger.error("SQLException: " + ex.getMessage());
            throw ex;
        }
        connection.close();
        return data;
    }


    public static MetadataTable getMetadataTableFromCustomMeta(String tableName) throws SQLException {
        String query = "select entity_nm_rus, attribute_nm_rus, attribute_nm_eng " +
                "from metadata_tech.metalayer_base_table " +
                "where entity_nm_eng=?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement stmnt = connection.prepareStatement(query);
        stmnt.setString(1, tableName);

        MetadataTable metadata = buildMetadataTable(stmnt, tableName);
        connection.close();
        return metadata;
    }

    private static MetadataTable buildMetadataTable(PreparedStatement stmnt, String tableName) throws SQLException {
        MetadataTable metadata = new MetadataTable(tableName);
        String entity_nm_rus = "";
        List<String> attribute_nm_rus = new ArrayList<>();
        List<String> attribute_nm_eng = new ArrayList<>();

        try {
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                entity_nm_rus = rs.getString("entity_nm_rus");
                attribute_nm_rus.add(rs.getString("attribute_nm_rus"));
                attribute_nm_eng.add(rs.getString("attribute_nm_eng"));
            }
        } catch (SQLException ex) {
            logger.error("SQLException: " + ex.getMessage());
            throw ex;
        }
        metadata.setTableNameRUS(entity_nm_rus);
        metadata.setColumnNamesRUS(attribute_nm_rus);
        metadata.setColumnNames(attribute_nm_eng);
        return metadata;
    }

    public static MetadataTable getMetadataTableFromMySQLMeta(String tableName) throws SQLException {
        String query = "select c.`column_name` as attribute_nm_eng, " +
                "c.column_comment as attribute_nm_rus, " +
                "t.table_comment as entity_nm_rus \n" +
                "from `information_schema`.`columns` as c \n" +
                "join `information_schema`.`tables`  as t\n" +
                "on c.`table_name` = t.`table_name` \n" +
                "where c.`table_schema`='tech_facilities' \n" +
                "    and c.`table_name`=?;";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement stmnt = connection.prepareStatement(query);
        stmnt.setString(1, tableName);
        MetadataTable metadata = buildMetadataTable(stmnt, tableName);
        connection.close();
        return metadata;
    }

    public static List<ReportMetadata> getMetadataReports() throws SQLException {
        Connection connection = ConnectionUtil.getConnection();

        String query = "select report_nm_rus, `description`, source_nm " +
                "from metadata_tech.metalayer_report";

        List<ReportMetadata> data = new ArrayList<>();
        logger.debug(query);
        try {
            ResultSet rs = connection.createStatement().executeQuery(query);
            while (rs.next()) {
                data.add(new ReportMetadata(
                        rs.getString("report_nm_rus"),
                        rs.getString("description"),
                        rs.getString("source_nm")
                ));
            }
        } catch (SQLException ex) {
            logger.error("SQLException: " + ex.getMessage());
            throw ex;
        }
        try {
            connection.close();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
        return data;
    }
}
