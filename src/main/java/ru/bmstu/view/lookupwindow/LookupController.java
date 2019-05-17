package ru.bmstu.view.lookupwindow;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.bmstu.VistaNavigator;
import ru.bmstu.view.utils.Context;

public class LookupController {
    @FXML
    private JFXButton selectPowerControlBoard;

    @FXML
    private JFXButton selectChangeoverPanelLight;

    @FXML
    private JFXButton selectCableLine;

    @FXML
    private JFXButton selectPowerRank;

    @FXML
    private JFXButton returnMainMenu;

    private Logger logger = LogManager.getLogger(getClass().getName());

    @FXML
    private void initialize() {
        this.selectPowerControlBoard.setOnAction(new ButtonHandler("power_control_board"));
        this.selectChangeoverPanelLight.setOnAction(new ButtonHandler("changeover_panel_light"));
        this.selectCableLine.setOnAction(new ButtonHandler("cable_line"));
        this.selectPowerRank.setOnAction(new ButtonHandler("power_rank"));

    }

    @FXML
    void returnMainMenuHandler(ActionEvent event) {
        VistaNavigator.loadVista(VistaNavigator.START_WINDOW);
    }

    private class ButtonHandler implements EventHandler<ActionEvent> {

        private final String appropriateTableName;

        private ButtonHandler(String appropriateTableName) {
            this.appropriateTableName = appropriateTableName;
        }

        @Override
        public void handle(ActionEvent event) {
            logger.info("open " + this.appropriateTableName);
            Object controller = VistaNavigator.openNewVista(VistaNavigator.GENERAL_LOOK_UP_WINDOW);
            Context.addContextObject(controller.toString(), appropriateTableName);
        }
    }
}
