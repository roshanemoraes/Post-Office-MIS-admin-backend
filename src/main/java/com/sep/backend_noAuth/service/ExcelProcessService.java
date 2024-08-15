package com.sep.backend_noAuth.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

public class ExcelProcessService {
    private static final List<String> REQUIRED_HEADERS = Arrays.asList("Name", "House No.", "Zone", "Town", "Mail Type");
    public static List<Map<String, Object>> convertExcelRows(MultipartFile file) throws IOException {

        // Convert multipart file into Excel workbook object
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        int sheetIndex = workbook.getActiveSheetIndex();

        // Get sheet
        XSSFSheet sheet = workbook.getSheetAt(sheetIndex);

        // Process headers
        List<String> headers = new ArrayList<>();
        Iterator<Row> rows = sheet.rowIterator();

        if (!rows.hasNext()) {
            throw new IllegalArgumentException("Excel file is empty.");
        }

        Row headerRow = rows.next();
        headerRow.forEach(cell -> headers.add(cell.getStringCellValue().trim()));

        // Validate headers
        if (!validateHeaders(headers)) {
            throw new IllegalArgumentException("Excel file headers are invalid. Expected headers: " + REQUIRED_HEADERS);
        }

        // Process remaining rows
        List<Map<String, Object>> rowsResult = new ArrayList<>();
        rows.forEachRemaining(row -> {
            Map<String, Object> rowMap = new LinkedHashMap<>();
            for (int i = 0; i < headers.size(); i++) {
                rowMap.put(headers.get(i), row.getCell(i) != null ? row.getCell(i).toString() : null);
            }

            // Add to list
            rowsResult.add(rowMap);
        });

        return rowsResult;
    }
    private static boolean validateHeaders(List<String> headers) {
        return headers.size() == REQUIRED_HEADERS.size() && headers.containsAll(REQUIRED_HEADERS);
    }
}
