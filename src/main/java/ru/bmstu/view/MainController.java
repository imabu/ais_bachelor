package ru.bmstu.view;

import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;

public class MainController {
    private String WEBSITE = "https://github.com/imabu/ais_bachelor/wiki";
    private HostServicesDelegate hostServices;
    @FXML
    private BorderPane vistaHolder;
    @FXML
    private Hyperlink hyperlinkAbout;

    @FXML
    private void initialize() {
        hyperlinkAbout.setTooltip(new Tooltip("Нажмите для получения подробной информации"));
    }

    public void createHostServices(Application app) {
        this.hostServices = HostServicesFactory.getInstance(app);
    }

    public void setVista(Node node) {
        vistaHolder.setCenter(node);
    }

    public BorderPane getVistaHolder() {
        return vistaHolder;
    }

    @FXML
    void openAbout(ActionEvent event) {
        hostServices.showDocument(WEBSITE);
    }
}
