package ru.bmstu.database.models;

public class ReportMetadata {

    private String reportName;
    private String description;
    private String source;

    public ReportMetadata(String reportName, String description, String source) {
        this.reportName = reportName;
        this.description = description;
        this.source = source;
    }

    public String getReportName() {
        return reportName;
    }

    public String getDescription() {
        return description;
    }

    public String getSource() {
        return source;
    }


}
