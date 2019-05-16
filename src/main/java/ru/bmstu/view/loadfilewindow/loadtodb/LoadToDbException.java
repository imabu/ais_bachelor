package ru.bmstu.view.loadfilewindow.loadtodb;

class LoadToDbException extends Exception {
    static String PARSE_DATE_ERROR = "Некорректная дата: ";

    LoadToDbException(String errorMsg, String wrongData) {
        super(errorMsg + wrongData);
    }
}
