package com.sep.backend_noAuth.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

public class ExcelProcessService {
    public static List<Map<String, Object>> convertExcelRows(MultipartFile file) throws IOException {

        // convert multipart file into excel workbook object
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        int sheetIndex = workbook.getActiveSheetIndex();

        // get sheet
        XSSFSheet sheet = workbook.getSheetAt(sheetIndex);

        // process Headers

        List<String> headers = new ArrayList<>();
        Iterator<Row> rows = sheet.rowIterator();
        //consider row number 1 as header row(means first row)
        rows.next().forEach(h -> headers.add(h.getStringCellValue()));

        // process remaining rows
        List<Map<String,Object>> rowsResult = new ArrayList<>();
        rows.forEachRemaining(row->{
            Map<String,Object> rowMap = new LinkedHashMap<>();
            for(int i=0; i<row.getPhysicalNumberOfCells(); i++){
                rowMap.put(headers.get(i), row.getCell(i).toString());
            }

            //add to list
            rowsResult.add(rowMap);
        });
        return rowsResult;
    }
}
