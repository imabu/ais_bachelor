package ru.bmstu.database;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileMetadataHelper {
    private static Logger logger = LogManager.getLogger(FileMetadataHelper.class.getName());

    public static List<String> getSheetsNames(String filename) throws SQLException {
        Connection connection = ConnectionUtil.getConnection() ;
        List<String> sheets = new ArrayList<>();
        try {
            String query = "select sheet_nm  " +
                    "from metadata_tech.v_file_sheet " +
                    "where excel_file_nm = ? ";
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, filename);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                sheets.add(rs.getString("sheet_nm"));
            }
            st.close();
        } catch (SQLException ex) {
            logger.error("SQLException: " + ex.getMessage());
            throw ex;
        }
        connection.close();
        return sheets;
    }

    public static List<String> getColumnMetadata(String filename,
                                                 String sheetname) throws SQLException {
        Connection connection = ConnectionUtil.getConnection() ;
        List<String> columnMeta = new ArrayList<>();
        try {
            String query = "select data_type  " +
                    "from metadata_tech.v_file_column " +
                    "where excel_file_nm = ? " +
                    "and sheet_nm = ? " +
                    "order by column_order_num";
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, filename);
            st.setString(2, sheetname);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                columnMeta.add(rs.getString("data_type"));
            }
            st.close();
        } catch (SQLException ex) {
            logger.error("SQLException: " + ex.getMessage());
            throw ex;
        }
        connection.close();
        return columnMeta;

    }

    public static String getStgTableNM(String filename,
                                       String sheetname) throws SQLException {
        Connection connection = ConnectionUtil.getConnection() ;

        String stgTableNm = "";
        try {
            String query = "select stg_table_nm  " +
                    "from metadata_tech.v_file_sheet_x_stg_table " +
                    "where file_nm = ? and sheet_nm=? ";
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, filename);
            st.setString(2, sheetname);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                stgTableNm = rs.getString("stg_table_nm");
            }
        } catch (SQLException ex) {
            logger.error("SQLException: " + ex.getMessage());
            throw ex;
        }
        connection.close();
        return stgTableNm;

    }
}
