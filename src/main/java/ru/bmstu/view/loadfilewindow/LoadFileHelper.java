package ru.bmstu.view.loadfilewindow;

import javafx.stage.FileChooser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.bmstu.VistaNavigator;
import ru.bmstu.parsingexcel.MetadataExcelSheet;
import ru.bmstu.parsingexcel.MetadataExcelSheetConstructor;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoadFileHelper {
    private static Logger logger = LogManager.getLogger(LoadFileHelper.class.getName());
    public static Path getFile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel(.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(VistaNavigator.getPrimaryStage());
        if (file != null) {
            return Paths.get(file.getPath());
        }
        return null;
    }

    public static LoadFileTask getLoadTask(Path selectedFile) {
        List<MetadataExcelSheet> sheetsMeta = new ArrayList<>();
        try {
            sheetsMeta = MetadataExcelSheetConstructor.get(selectedFile);
        } catch (SQLException e) {
            logger.error(e);
        }
        return new LoadFileTask(sheetsMeta);
    }


}
