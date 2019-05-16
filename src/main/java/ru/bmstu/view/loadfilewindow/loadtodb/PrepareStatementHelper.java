package ru.bmstu.view.loadfilewindow.loadtodb;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class PrepareStatementHelper {
    private static Logger logger = LogManager.getLogger(PrepareStatementHelper.class.getName());

    public static void setVariableToStatement(PreparedStatement preparedStmnt,
                                              int positionInQuery,
                                              String datatype,
                                              Object data) throws SQLException, LoadToDbException {
        logger.debug("column number: " + positionInQuery + " ; data: " + data + " ; data type: " + datatype);
        switch (datatype) {
            case "integer":
                setInteger(preparedStmnt, positionInQuery, data);
                break;
            case "string":
                setString(preparedStmnt, positionInQuery, data);
                break;
            case "date":
                setDate(preparedStmnt, positionInQuery, data);
                break;
            case "double":
                setDouble(preparedStmnt, positionInQuery, data);
                break;
        }
    }

    private static void setInteger(PreparedStatement preparedStmnt, int positionInQuery, Object data) throws SQLException {
        try {
            if (Objects.equals(data, "")) {
                preparedStmnt.setNull(positionInQuery, Types.INTEGER);
            } else {
                preparedStmnt.setInt(positionInQuery, (int) Math.round((double) data));
            }
        } catch (SQLException ex) {
            logger.error("SQLException: " + ex.getMessage());
            throw ex;
        } catch (ClassCastException ex) {
            logger.error("Cast exception: " + ex.getMessage());
            throw ex;
        }
    }

    private static void setString(PreparedStatement preparedStmnt, int positionInQuery, Object data) throws SQLException {
        try {
            if (Objects.equals(data, "")) {
                preparedStmnt.setNull(positionInQuery, Types.VARCHAR);
            } else {
                preparedStmnt.setString(positionInQuery, (String) data);
            }
        } catch (SQLException ex) {
            logger.error("SQLException: " + ex.getMessage());
            throw ex;
        } catch (Exception ex){
            logger.error("Exception: " + ex.getMessage());
            throw ex;
        }
    }

    private static void setDate(PreparedStatement preparedStmnt, int positionInQuery, Object data) throws SQLException, LoadToDbException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date parsed;
        try {
            if (Objects.equals(data, "")) {
                preparedStmnt.setNull(positionInQuery, Types.DATE);
            } else {
                parsed = dateFormat.parse((String) data);
                preparedStmnt.setDate(positionInQuery, new Date(parsed.getTime()));
            }
        } catch (ParseException e) {
            logger.error("Wrong date " + data);
            throw new LoadToDbException(LoadToDbException.PARSE_DATE_ERROR, (String) data);
        } catch (SQLException ex) {
            logger.error("SQLException: " + ex.getMessage());
            throw ex;
        }
    }

    private static void setDouble(PreparedStatement preparedStmnt, int positionInQuery, Object data) throws SQLException {
        try {
            if (Objects.equals(data, "")) {
                preparedStmnt.setNull(positionInQuery, Types.DOUBLE);
            } else {
                preparedStmnt.setDouble(positionInQuery, (double) data);
            }
        } catch (SQLException ex) {
            logger.error("SQLException: " + ex.getMessage());
            throw ex;
        } catch (ClassCastException ex) {
            logger.error("Cast exception: " + ex.getMessage());
            throw ex;
        }
    }
}
