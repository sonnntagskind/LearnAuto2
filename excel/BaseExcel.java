package battlemetrics_rust.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;

import java.io.*;

import static battlemetrics_rust.pages.LogsPage.getServerID;

public abstract class BaseExcel {
    File file;
    FileInputStream inputStream;
    FileOutputStream outputStream;
    HSSFWorkbook workbook;
    HSSFSheet sheet;
    HSSFCell cell;
    CellStyle cellStyle;
    CreationHelper createHelper;
    int i;

    public void readOrCreateFile() throws IOException {
        try {
            file = new File("C:/battlemetrics/" + getServerID() + ".xls");
            inputStream = new FileInputStream(file);
            workbook = new HSSFWorkbook(inputStream);
            sheet = workbook.getSheetAt(0);
        } catch (FileNotFoundException e) {
            file = new File("C:/battlemetrics/" + getServerID() + ".xls");
            file.getParentFile().mkdirs();
            workbook = new HSSFWorkbook();
            sheet = workbook.createSheet("Logs");
            sheet.createRow(0);
            sheet.getRow(0).createCell(0).setCellValue("Record ID");
            sheet.getRow(0).createCell(1).setCellValue("Player ID");
            sheet.getRow(0).createCell(2).setCellValue("Player Name");
            sheet.getRow(0).createCell(3).setCellValue("Log In");
            sheet.getRow(0).createCell(4).setCellValue("Log Out");
            sheet.getRow(0).createCell(5).setCellValue("Duration");
        }
    }

    public void writeFile() throws IOException {
        try { inputStream.close();
        } catch (NullPointerException e) { }
        outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();
    }
}
