package com.app.service;

import com.app.entity.cars.Brand;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
public class BulkCarUploadService {

    // Implement logic to upload brand names from a file and return a list of Brand objects
    public List<Brand> readExcel(String filePath) throws IOException {
      List<Brand> entities = new ArrayList<>();

      // Implement logic to read the Excel file and populate the brands list
      FileInputStream fis = new FileInputStream(new File(filePath));

      // Implement logic to iterate over the rows and columns to populate the brands list
      Workbook workbook = new XSSFWorkbook(fis);

      // Assuming the first sheet contains brand names at index 0
      Sheet sheet = workbook.getSheetAt(0);
      Iterator<Row> rowIterator = sheet.iterator();

      //skip the header row
      if (rowIterator.hasNext()) {
          rowIterator.next();
      }

      //read each row as it has name and id
      while (rowIterator.hasNext()) {
          Row row = rowIterator.next();
          Brand entity = new Brand();

          //Assuming the first column is ID and second column is name
          entity.setId((long)row.getCell(0).getNumericCellValue()); //here numeric read double so convert to long
          entity.setName(row.getCell(1).getStringCellValue());

          //adding all the entities
          entities.add(entity);
      }

      workbook.close();
      fis.close();
      return entities;
    }

}
