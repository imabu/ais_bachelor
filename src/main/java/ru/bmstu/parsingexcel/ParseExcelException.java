package ru.bmstu.parsingexcel;

public class ParseExcelException extends Exception {
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public ParceExceptionType getType() {
        return type;
    }

    private String additionalInfo;
    private ParceExceptionType type;
    ParseExcelException(String errorMsg, String additionalInfo, ParceExceptionType type) {
        super(errorMsg);
        this.additionalInfo = additionalInfo;
        this.type = type;
    }

    public enum ParceExceptionType {
        SHEET_NOT_FOUND
    }
}
