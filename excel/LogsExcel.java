package battlemetrics_rust.excel;

import battlemetrics_rust.model.Logs;
import battlemetrics_rust.pages.LogsPage;
import battlemetrics_rust.utils.DateUtil;
import battlemetrics_rust.utils.Excel;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class LogsExcel extends Excel {

    private List<Logs> logs = new ArrayList<Logs>();
    private List<String> listRecordID = new ArrayList<String>();
    private List<Logs> logsOffline = new ArrayList<Logs>();
    private List<Logs> logsOnline = new ArrayList<Logs>();
    private List<String> listPlayerID = new ArrayList<String>();

    private Date parseStringToDate(String stringTime) throws ParseException {
        return new DateUtil().parseStringToDate(stringTime);
    }

    public void insertLogs(String serverID) throws ParseException, IOException {

        readOrCreateFile("C:/battlemetrics/" + serverID + ".xls");

        cellStyle = workbook.createCellStyle();
        createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d MMM HH:mm"));

        logs = new LogsPage().parseWebListsToStringLists();

        listRecordID = createListRecordID();

        logs = deleteDuplicatesFromLogs(logs, listRecordID);

        Collections.reverse(logs);

        logsOffline = splitOnlineType(logs, "css-5sq57v");
        logsOnline = splitOnlineType(logs, "css-k1knfq");

        FindBlankRow();

        InsertLogsOnline(logsOnline);

        listPlayerID = createListPlayerID();

        insertOfflineTime(logsOffline, listPlayerID);

        writeFile();
    }

    private List<String> createListRecordID() {
        List<String> list = new ArrayList<String>();

        for (Row row : sheet) {
            String s = row.getCell(0).getStringCellValue();
            list.add(s);
        }
        return list;
    }

    private List<Logs> deleteDuplicatesFromLogs(List<Logs> listLogs, List<String> listRecordID) {
        return listLogs.stream().filter(log -> !listRecordID.contains(log.getRecordID())).collect(Collectors.toList());
    }

    private List<Logs> splitOnlineType(List<Logs> listLogs, String onlineType) {
        return listLogs.stream().filter(log -> log.getOnlineType().equals(onlineType)).collect(Collectors.toList());
    }

    private int FindBlankRow() {
        for (i = 0; ; i++) {
            try {
                if (sheet.getRow(i).getCell(0) == null) {
                    break;
                }
            } catch (NullPointerException e) {
                break;
            }
        }
        return i;
    }

    private void insertData(Logs l) throws ParseException {
        sheet.getRow(i).createCell(0).setCellValue(l.getRecordID());
        sheet.getRow(i).createCell(1, CellType.NUMERIC).setCellValue(Integer.parseInt(l.getPlayerID()));
        sheet.getRow(i).createCell(2).setCellValue(l.getPlayerName());

        cell = sheet.getRow(i).createCell(3);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(parseStringToDate(l.getLastSeenTime()));

        cell = sheet.getRow(i).createCell(5, CellType.FORMULA);
        i += 1;
        cell.setCellFormula("if(isblank(E" + i + "),\"\",(ROUNDDOWN(((E" + i + "-D" + i + ")*24),0))&\":\"&(ROUND(((((E" + i + "-D" + i + ")*24)-(ROUNDDOWN(((E" + i + "-D" + i + ")*24),0)))*60),0)))");
        i -= 1;
    }

    private void InsertLogsOnline(List<Logs> logsOnline) throws ParseException {
        for (Logs l : logsOnline) {
            try {
                i++;
                insertData(l);
            } catch (NullPointerException e) {
                sheet.createRow(i - 1);
                i -= 1;
                insertData(l);
                i += 1;
            }
        }
    }

    private List<String> createListPlayerID() {
        List<String> list = new ArrayList<String>();
        createListPlayerID:
        for (Row row : sheet) {
            try {
                String s = String.valueOf((int) row.getCell(1).getNumericCellValue());
                list.add(s);
            } catch (IllegalStateException e) {
            }
        }
        return list;
    }

    private void insertOfflineTime(List<Logs> logsOffline, List<String> listPlayerID) throws ParseException {
        for (int i = 0; i < logsOffline.size(); i++) {
            for (int j = (listPlayerID.size() - 1); j > 0; j--) {
                if ((logsOffline.get(i).getPlayerID()).equals(listPlayerID.get(j))) {
                    cell = sheet.getRow(j + 1).createCell(4);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(parseStringToDate(logsOffline.get(i).getLastSeenTime()));
                    break;
                }
            }
        }
    }
}