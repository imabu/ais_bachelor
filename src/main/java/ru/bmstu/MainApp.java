package ru.bmstu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.bmstu.database.ConnectionUtil;
import ru.bmstu.view.MainController;
import ru.bmstu.view.modals.AlertVista;
import ru.bmstu.view.utils.Context;


import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainApp extends Application {
    private Logger logger = LogManager.getLogger(getClass().getName());

    public static void main(String[] args) {
        setEnvironment();
        launch(args);
    }

    private static void setEnvironment() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = new Date();
        System.setProperty("logdir", "C:\\Users\\Karina.Kuchaeva\\Documents\\PersonalProjects\\etl_core\\tmp");
        System.setProperty("logfilename", "main_" + dateFormat.format(date));
        System.setProperty("tech_user", "root");
        System.setProperty("tech_user_password", "admin");
        System.setProperty("mysql_url", "jdbc:mysql://localhost:3306/default");
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(VistaNavigator.APP_NM);
        Pane rootLayer = initRootLayout();
        VistaNavigator.loadVista(VistaNavigator.START_WINDOW);
        Scene scene = new Scene(rootLayer);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(String.valueOf(MainApp.class.getResource(VistaNavigator.ICON_FILE))));
        primaryStage.show();
        ConnectionUtil.checkConnection();
        Context.init();
    }

    private Pane initRootLayout() {
        BorderPane rootLayout = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            URL rootLayoutPath = MainApp.class.getResource(VistaNavigator.MAIN);
            logger.info("Root layout fxml-file: " + rootLayoutPath);
            if (rootLayoutPath == null){
                AlertVista.throwAlertError("Внутренняя ошибка: не найден корневой слой. Приложение устсановлено неверно");
            }else {
                loader.setLocation(rootLayoutPath);
                rootLayout = (BorderPane) loader.load();
                MainController mainController = loader.getController();
                VistaNavigator.setMainController(mainController);
                mainController.createHostServices(this);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return rootLayout;
    }

}