package ru.bmstu.database;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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
        try {
            ResultSet rs = connection.createStatement().executeQuery(query);
            while (rs.next()) {
                List<Object> row = new ArrayList<>();
                for (String column : metadata.getColumnNames()) {
                    row.add(rs.getString(column));
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

    public static MetadataTable getMetadataTable(String tableName) throws SQLException {
        MetadataTable metadata = new MetadataTable(tableName);
        Connection connection = ConnectionUtil.getConnection();

        String query = "select entity_nm_rus, attribute_nm_rus, attribute_nm_eng " +
                "from metadata_tech.metalayer_base_table " +
                "where entity_nm_eng=?";

        PreparedStatement stmnt = connection.prepareStatement(query);
        stmnt.setString(1, tableName);

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
        connection.close();
        return metadata;
    }
}
