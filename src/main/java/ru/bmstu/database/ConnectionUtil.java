package ru.bmstu.database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.bmstu.view.modals.AlertVista;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtil {
    private static BasicDataSource dataSource;
    private static Logger logger = LogManager.getLogger(ConnectionUtil.class.getName());

    private static BasicDataSource init() {
        if (dataSource == null) {
            dataSource = new BasicDataSource();
            dataSource.setUrl(System.getProperty("mysql_url"));
            dataSource.setUsername(System.getProperty("tech_user"));
            dataSource.setPassword(System.getProperty("tech_user_password"));

            String connectionProperties = "serverTimezone=UTC";
            dataSource.setConnectionProperties(connectionProperties);
            dataSource.setMinIdle(1);
            dataSource.setMaxIdle(10);

            logger.info("Configuration of connection pool to MySql: ");
            logger.info("Properties: " + connectionProperties);
            logger.info("min idle : " + dataSource.getMinIdle());
            logger.info("max idle : " + dataSource.getMaxIdle());
        }
        return dataSource;
    }
    public static boolean checkConnection(){
        getConnection();
        return true;
    }

    public static Connection getConnection() {
        if (dataSource == null) {
            init();
        }
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error(e);
            AlertVista.throwAlertError("Нет подключения к базе данных");
        }
        return null;
    }

}
