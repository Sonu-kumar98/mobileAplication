package com.impactqa.utilities;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


/**
 * @author Sonu kumar
 * @version 1.0
 * @description This class is Util class for reading and writing data from an excel source.
 * @since 22020-09-12
 */
public class ExcelUtil {


    String action[]= {"Information","Call","Website"};

    private Workbook wb;
    private Sheet sheet;

    /**
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


    /**
     * This will read get all cell values from the header row (1st row) from the given @sheet
     *
     * @param sheet
     * @return list of headers
     */

    private List<String> getColumns(Sheet sheet) {
        final Row row = sheet.getRow(0);
        final List<String> columnValues = new ArrayList<>();
        final int firstCellNum = row.getFirstCellNum();
        final int lastCellNum = row.getLastCellNum();
        for (int i = firstCellNum; i < lastCellNum; i++) {
            final Cell cell = row.getCell(i);
            columnValues.add(readCells(cell));
        }
        return columnValues;
    }

    /**
     * This will iterate all rows to match the row for the given dataID
     * Once it finds the spefice data row, it will create key-value (Map) from header row (1st row) and data row (1+n th row)
     *
     * @param dataID
     * @return key-value (Map) from header row (1st row) and data row (1+n th row)
     */
    public Map<String, String> getRowDataMtahcingDataId(String dataID) {
        final List<String> rowData = new ArrayList<>();
        final Map<String, String> rowVal = new LinkedHashMap<>();
        Object value;
        final List<String> coulmnNames = getColumns(sheet);
        final int totalRows = sheet.getPhysicalNumberOfRows();
        final Row row = sheet.getRow(0);
        final int firstCellNum = row.getFirstCellNum();
        final int lastCellNum = row.getLastCellNum();
        for (int i = 1; i < totalRows; i++) {
            final Row rows = sheet.getRow(i);
            final String testLinkID = rows.getCell(0).getStringCellValue();
            if (dataID.equalsIgnoreCase(testLinkID)) {
                for (int j = firstCellNum; j < lastCellNum; j++) {
                    final Cell cell = rows.getCell(j);
                    if (cell == null
                            || cell.getCellTypeEnum() == CellType.BLANK) {
                        rowData.add("");
                    } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                        if (DateUtil.isCellDateFormatted(cell)) {
                            rowData.add(cell.getDateCellValue().toString());
                        } else {
                            final Double val = cell.getNumericCellValue();
                            value = val.intValue();
                            rowData.add(value.toString());
                        }
                    } else if (cell.getCellTypeEnum() == CellType.STRING) {
                        rowData.add(cell.getStringCellValue());
                    } else if (cell.getCellTypeEnum() == CellType.FORMULA) {
                        rowData.add(cell.getStringCellValue());
                    } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
                        rowData.add(String.valueOf(cell.getBooleanCellValue()));
                    } else if (cell.getCellTypeEnum() == CellType.ERROR) {
                        throw new RuntimeException(" Cell Type is not supported ");
                    }
                    rowVal.put(coulmnNames.get(j), rowData.get(j).trim());
                }
                break;
            }

        }
        return rowVal;
    }

    public List<Map<String, String>> getAllRows() {
        final List<Map<String, String>> retList = new LinkedList<>();

        Object value;
        final List<String> coulmnNames = getColumns(sheet);
        final int totalRows = sheet.getPhysicalNumberOfRows();
        final Row row = sheet.getRow(0);
        final int firstCellNum = row.getFirstCellNum();
        final int lastCellNum = row.getLastCellNum();
        for (int i = 1; i < totalRows; i++) {
            final Row rows = sheet.getRow(i);
            final String cell0 = rows.getCell(0).getStringCellValue();
            if ((!"".equalsIgnoreCase(cell0))) {
                final List<String> rowData = new ArrayList<>();
                final Map<String, String> rowVal = new LinkedHashMap<>();

                for (int j = firstCellNum; j < lastCellNum; j++) {
                    final Cell cell = rows.getCell(j);
                    if (cell == null
                            || cell.getCellTypeEnum() == CellType.BLANK) {
                        rowData.add("");
                    } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                        final Double val = cell.getNumericCellValue();
                        value = val.intValue();
                        rowData.add(value.toString());
                    } else if (cell.getCellTypeEnum() == CellType.STRING) {
                        rowData.add(cell.getStringCellValue());
                    } else if (cell.getCellTypeEnum() == CellType.FORMULA) {
                        rowData.add(cell.getStringCellValue());
                    } else if (DateUtil.isCellDateFormatted(cell)) {
                        rowData.add(cell.getDateCellValue().toString());
                    } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
                        rowData.add(cell.getStringCellValue());
                    } else if (cell.getCellTypeEnum() == CellType.ERROR) {
                        throw new RuntimeException(" Cell Type is not supported ");
                    }
                    rowVal.put(coulmnNames.get(j), rowData.get(j).trim());
                }
                retList.add(rowVal);
            }

        }
        return retList;

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

    // Change Made by Sonu Kumar
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

    public List<Map<String, String>> getRowsValue(int startRow, int endRow) {
        final List<Map<String, String>> rowData = new ArrayList<>();
        final Row row = sheet.getRow(0);
        for (int i = startRow; i <= endRow; i++) {
            rowData.add(getRowDataMatchingRowId(i));
        }
        return rowData;
    }


    public Map<String, String> getRowDataMatchingRowId(int id) {
        final List<String> rowData = new ArrayList<>();
        final Map<String, String> rowVal = new LinkedHashMap<>();
        Object value;
        final List<String> coulmnNames = getColumns(sheet);
        final int totalRows = sheet.getPhysicalNumberOfRows();
        final Row row = sheet.getRow(0);
        final int firstCellNum = row.getFirstCellNum();
        final int lastCellNum = row.getLastCellNum();
        final Row rows = sheet.getRow(id);
        final String testLinkID = rows.getCell(0).getStringCellValue();
        for (int j = firstCellNum; j < lastCellNum; j++) {
            final Cell cell = rows.getCell(j);
            if (cell == null
                    || cell.getCellTypeEnum() == CellType.BLANK) {
                rowData.add("");
            } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                if (DateUtil.isCellDateFormatted(cell)) {
                    rowData.add(cell.getDateCellValue().toString());
                } else {
                    final Double val = cell.getNumericCellValue();
                    value = val.intValue();
                    rowData.add(value.toString());
                }
            } else if (cell.getCellTypeEnum() == CellType.STRING) {
                rowData.add(cell.getStringCellValue());
            } else if (cell.getCellTypeEnum() == CellType.FORMULA) {
                rowData.add(cell.getStringCellValue());
            } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
                rowData.add(String.valueOf(cell.getBooleanCellValue()));
            } else if (cell.getCellTypeEnum() == CellType.ERROR) {
                throw new RuntimeException(" Cell Type is not supported ");
            }
            rowVal.put(coulmnNames.get(j), rowData.get(j).trim());
        }


        return rowVal;

    }


}
