package ru.bmstu.view.loadfilewindow.selectfile;

import javafx.concurrent.Task;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.bmstu.parsingexcel.MetadataExcelSheet;
import ru.bmstu.parsingexcel.ParserExcelSheet;

import java.util.List;

public class LoadFileToAppTask extends Task<Boolean> {

    private Logger logger = LogManager.getLogger(getClass().getName());
    private List<MetadataExcelSheet> sheetsMeta;
    //TODO: вынести в общий мехнизм для Tasks
    private StringBuilder logMessage = new StringBuilder();
    private boolean isSuccess;

    LoadFileToAppTask(List<MetadataExcelSheet> sheetsMeta) {
        this.sheetsMeta = sheetsMeta;
    }

    private void updateLogOut(String message) {
        this.logMessage.append(message).append("\n");
        updateMessage(logMessage.toString());
    }

    public List<MetadataExcelSheet> getSheetsMeta() {
        return sheetsMeta;
    }

    @Override
    protected Boolean call() throws Exception {
        double numberOfSheets = this.sheetsMeta.size();
        if (numberOfSheets > 0) {
            updateProgress(0, numberOfSheets);
            updateLogOut("Загрузка файла " + this.sheetsMeta.get(0).getFilepath() + "...");
            for (int i = 0; i < numberOfSheets; i += 1) {
                MetadataExcelSheet sheet = this.sheetsMeta.get(i);
                ParserExcelSheet parser = new ParserExcelSheet(sheet);
                updateLogOut("Началась загрузка листа " + sheet.getSheetName() + "...");
                parser.parse();
                updateLogOut("Загрузка листа закончена. Количество строк в листе: " + sheet.getRowNumber());
                updateProgress(i, numberOfSheets);
            }
            updateProgress(numberOfSheets, numberOfSheets);
            this.isSuccess = true;
            updateLogOut("Файл загружен");
        } else {
            logMessage.append("Загружаемый файл не зарегистрирован. Проверьте правильность выбранного файла и его структуры\n");
            updateMessage(logMessage.toString());
            this.isSuccess = false;
        }

        return this.isSuccess;
    }

    public boolean getStatus() {
        return this.isSuccess;
    }
}
