package battlemetrics_rust.steps;

import battlemetrics_rust.excel.LogsExcel;
import battlemetrics_rust.pages.LogsPage;

import java.io.IOException;
import java.text.ParseException;

public class LogsSteps {

    private LogsPage logsPage = new LogsPage();
    private LogsExcel logsExcel = new LogsExcel();

    public LogsSteps enterPage(String text) {
        logsPage.enterPage(text);
        return this; }

    public LogsSteps printStatistics() {
        logsPage.printStatistics();
        return this; }

    public LogsSteps parseWebListsToStringLists() throws ParseException {
        logsPage.parseWebListsToStringLists();
        return this; }

    public LogsSteps writeExcel(String serverID) throws ParseException, IOException {
        logsExcel.insertLogs(serverID);
        return this; }
}
