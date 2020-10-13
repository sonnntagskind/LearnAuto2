package battlemetrics_rust.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Excel {
    public File file;
    public FileInputStream inputStream;
    public FileOutputStream outputStream;
    public HSSFWorkbook workbook;
    public HSSFSheet sheet;
    public HSSFCell cell;
    public CellStyle cellStyle;
    public CreationHelper createHelper;
    public int i;

    public void readOrCreateFile(String filePath) throws IOException {

        file = new File(filePath);

        if (file.exists()) {
            inputStream = new FileInputStream(file);
            workbook = new HSSFWorkbook(inputStream);
            sheet = workbook.getSheetAt(0);
        } else {
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
        if (inputStream != null) {
            inputStream.close();
        }
        outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();
    }
}
