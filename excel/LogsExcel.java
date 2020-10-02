package battlemetrics_rust.excel;

import battlemetrics_rust.model.Logs;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static battlemetrics_rust.model.LogsDAO.createListLogs;
import static battlemetrics_rust.pages.BasePage.parseStringToDate;

public class LogsExcel extends BaseExcel {

    public void insertLogs() throws ParseException, IOException {

        readOrCreateFile();

        cellStyle = workbook.createCellStyle();
        createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d MMM HH:mm"));

        List<Logs> logs = createListLogs();

        List<String> listRecordID = new ArrayList<String>();
        getListRecordID:
        for (Row row : sheet) {
            String recordID = row.getCell(0).getStringCellValue();
            listRecordID.add(recordID); }
        deleteDuplicatesFromLogs:
        logs = logs.stream().filter(log -> !listRecordID.contains(log.getRecordID())).collect(Collectors.toList());

        reverseLogs:
        Collections.reverse(logs);

        List<Logs> logsOffline = logs.stream().filter(log -> log.getOnlineType().equals("css-5sq57v")).collect(Collectors.toList());
        List<Logs> logsOnline = logs.stream().filter(log -> log.getOnlineType().equals("css-k1knfq")).collect(Collectors.toList());

        FindBlankRow:
        for (i = 0; ; i++) {
            try { if (sheet.getRow(i).getCell(0) == null) { break; }
            } catch (NullPointerException e) { break; } }

        InsertLogsOnline:
        for (Logs l : logsOnline) {
            try {
                i++;
                insertData(l);
            } catch (NullPointerException e) {
                sheet.createRow(i - 1);
                i -= 1;
                insertData(l);
                i += 1; } }

        List<String> listPlayerID = new ArrayList<String>();
        createListPlayerID:
        for (Row row : sheet) {
            try { String playerID = String.valueOf((int) row.getCell(1).getNumericCellValue());
                listPlayerID.add(playerID);
            } catch (IllegalStateException e) { } }

        insertOfflineTime:
        for (int i = 0; i < logsOffline.size(); i++) {
            for (int j = (listPlayerID.size() - 1); j > 0; j--) {
                if ((logsOffline.get(i).getPlayerID()).equals(listPlayerID.get(j))) {
                    cell = sheet.getRow(j + 1).createCell(4);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(parseStringToDate(logsOffline.get(i).getLastSeenTime()));
                    break; } } }

        writeFile();
    }

    public void insertData(Logs l) throws ParseException {
        sheet.getRow(i).createCell(0).setCellValue(l.getRecordID());
        sheet.getRow(i).createCell(1, CellType.NUMERIC).setCellValue(Integer.parseInt(l.getPlayerID()));
        sheet.getRow(i).createCell(2).setCellValue(l.getPlayerName());

        cell = sheet.getRow(i).createCell(3);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(parseStringToDate(l.getLastSeenTime()));

        cell = sheet.getRow(i).createCell(5, CellType.FORMULA);
        i += 1;
        cell.setCellFormula("if(isblank(E" + i + "),\"\",(ROUNDDOWN(((E" + i + "-D" + i + ")*24),0))&\":\"&(ROUND(((((E" + i + "-D" + i + ")*24)-(ROUNDDOWN(((E" + i + "-D" + i + ")*24),0)))*60),0)))");
        i -= 1; }

}