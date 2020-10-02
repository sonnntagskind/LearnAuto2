package battlemetrics_rust.steps;

import battlemetrics_rust.excel.LogsExcel;
import battlemetrics_rust.pages.LogsPage;

import java.io.IOException;
import java.text.ParseException;

public class LogsSteps {

    private LogsPage logsPage = new LogsPage();
    private LogsExcel logsExcel = new LogsExcel();

    public LogsSteps _enterPage(String text) {
        logsPage.enterPage(text);
        return this; }

    public LogsSteps _printStatistics() {
        logsPage.printStatistics();
        return this; }

    public LogsSteps _parseWebListsToStringLists() throws ParseException {
        logsPage.parseWebListsToStringLists();
        return this; }

    public LogsSteps _writeExcel() throws ParseException, IOException {
        logsExcel.insertLogs();
        return this; }

}
