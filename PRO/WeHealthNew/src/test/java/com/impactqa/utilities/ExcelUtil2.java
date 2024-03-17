package com.impactqa.utilities;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author Maqdoom Sharief
 * @version 1.0
 * @description This class is Util class for reading and writing data from an excel source.
 * @since 22020-09-12
 */
public class ExcelUtil2 {

    private Workbook wb;
    private Sheet sheet;
    private Integer headerRow = 0;

    /**
     *
     * This will read excelsheet(@sheetName) from the given workbook @path
     *
     * @param path
     * @param sheetName
     */
    public void setWorkbook(String path, String sheetName) {
        try {
            wb = WorkbookFactory.create(new FileInputStream(path));
            sheet = wb.getSheet(sheetName);
        } catch (IOException e) {
            Assert.fail("Error occured while reading the excel file. Path: " + path, e);
        } catch (InvalidFormatException e) {
            Assert.fail("Error occured while reading the excel file. File format should be xls or xlsx Path: " + path, e);
        }
    }

    // we are adding the cell getCell Value
    public List<String> getCellsValue(int columnNumber, int startRow, int endRow) {
        final List<String> rowData = new ArrayList<>();
        final Row row = sheet.getRow(0);
        for (int i = startRow; i <= endRow; i++) {
            final Row rows = sheet.getRow(i);
            final Cell cell = rows.getCell(columnNumber);
            if (cell != null)
                rowData.add(readCells(cell));
        }
        return rowData;
    }


    private String readCells(Cell cell) {
        Object value;
        if (cell == null
                || cell.getCellTypeEnum() == CellType.BLANK) {
            return "";
        } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            final Double val = cell.getNumericCellValue();
            value = val.intValue();
            return value.toString();
        } else if (cell.getCellTypeEnum() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellTypeEnum() == CellType.FORMULA) {
            return cell.getStringCellValue();
        } else if (DateUtil.isCellDateFormatted(cell)) {
            return cell.getDateCellValue().toString();
        } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
            return cell.getStringCellValue();
        } else if (cell.getCellTypeEnum() == CellType.ERROR) {
            throw new RuntimeException(" Cell Type is not supported ");
        }
        return "";
    }


}
