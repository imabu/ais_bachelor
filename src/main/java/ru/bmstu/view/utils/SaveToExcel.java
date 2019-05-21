package ru.bmstu.view.utils;

import javafx.stage.FileChooser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.bmstu.VistaNavigator;
import ru.bmstu.parsingexcel.MetadataExcelSheet;
import ru.bmstu.parsingexcel.MetadataExcelSheetConstructor;
import ru.bmstu.parsingexcel.WritterToExcel;
import ru.bmstu.view.modals.OpenExcelDialog;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SaveToExcel {
    private static Logger logger = LogManager.getLogger(SaveToExcel.class.getName());

    public static void save(MetadataExcelSheet metaSheet, File fileOut) throws IOException {
        new WritterToExcel(metaSheet).write(fileOut);
        logger.info("Data was saved in " + fileOut.getAbsolutePath());
        OpenExcelDialog.SELECTED_BUTTON selected = OpenExcelDialog.show(fileOut.getAbsolutePath());
        logger.debug(selected);
        if (selected == OpenExcelDialog.SELECTED_BUTTON.BUTTON_OK) {
            logger.info("Open in Excel " + fileOut.getAbsolutePath());
            Desktop.getDesktop().open(fileOut);
        }
    }

    public static File getFileToSave(String initName) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName(initName + ".xlsx");
        return fileChooser.showSaveDialog(VistaNavigator.getPrimaryStage());
    }
}
