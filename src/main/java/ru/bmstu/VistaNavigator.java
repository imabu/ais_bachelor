package ru.bmstu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.bmstu.view.MainController;

import java.io.IOException;

public class VistaNavigator {
    public static final String MAIN = "/ru/bmstu/view/RootLayout.fxml";
    public static final String START_WINDOW = "/ru/bmstu/view/mainwindow/StartWindow.fxml";
    public static final String LOAD_FILE = "/ru/bmstu/view/loadfilewindow/selectfile/SelectFile.fxml";
    public static final String PREVIEW_FILE = "/ru/bmstu/view/loadfilewindow/selectfile/previewwindow/Preview.fxml";
    public static final String LOAD_TO_DB = "/ru/bmstu/view/loadfilewindow/loadtodb/LoadToDb.fxml";
    public static final String SELECT_REPORT = "/ru/bmstu/view/reports/SelectReport.fxml";
    public static final String LOOK_UP_WINDOW = "/ru/bmstu/view/lookupwindow/LookupWindow.fxml";
    public static final String GENERAL_LOOK_UP_WINDOW = "/ru/bmstu/view/lookupwindow/GeneralLookUpWindow.fxml";

    public static final String APP_NM = "АИС БлаБла";
    public static final String ICON_FILE = "/logo.png";
    private static Logger logger = LogManager.getLogger(VistaNavigator.class.getName());

    private static MainController mainController;

    public static void setMainController(MainController mainController) {
        VistaNavigator.mainController = mainController;
    }

    public static void loadVista(String fxml) {
        try {
            logger.info("load layout fxml-file: " + fxml);
            mainController.setVista(
                    FXMLLoader.load(VistaNavigator.class.getResource(fxml))
            );
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public static Object openNewVista(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(VistaNavigator.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(String.valueOf(VistaNavigator.class.getResource(ICON_FILE))));
            stage.setTitle(APP_NM);
            stage.setScene(scene);
            stage.show();
            return fxmlLoader.getController();
        } catch (IOException e) {
            logger.error("Failed to create new Window.", e);
        }
        return null;
    }

    public static Stage getPrimaryStage() {
        return (Stage) mainController.getVistaHolder().getScene().getWindow();
    }

}
