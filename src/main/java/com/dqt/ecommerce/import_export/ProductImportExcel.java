package com.dqt.ecommerce.import_export;

import com.dqt.ecommerce.model.Category;
import com.dqt.ecommerce.model.Product;
import com.dqt.ecommerce.repository.CategoryRepository;
import com.dqt.ecommerce.repository.ProductRepository;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Iterator;

@Component
public class ProductImportExcel {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public boolean saveDataFromUploadFile(MultipartFile file) {
        boolean isFlag = false;
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xlsx")) {
            isFlag = readDataFromExcel(file);
        }
        return isFlag;
    }

    private boolean readDataFromExcel(MultipartFile file) {
        Workbook workbook = getWorkBook(file);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();
        rows.next();
        while (rows.hasNext()) {
            Row row = rows.next();
            Product product = new Product();
//            if (row.getCell(0).getCellType() == CellType.NUMERIC) {
//                product.setId((long) row.getCell(0).getNumericCellValue());
//            }
            if (row.getCell(1).getCellType() == CellType.STRING) {
                product.setName(row.getCell(1).getStringCellValue());
            }
            if (row.getCell(2).getCellType() == CellType.NUMERIC) {
                product.setPrice((long) row.getCell(2).getNumericCellValue());
            }
            if (row.getCell(3).getCellType() == CellType.STRING) {
                product.setTitle(row.getCell(3).getStringCellValue());
            }
            if (row.getCell(4).getCellType() == CellType.NUMERIC) {
                product.setSale((int) row.getCell(4).getNumericCellValue());
            }
            if (row.getCell(5).getCellType() == CellType.NUMERIC) {
                product.setCost((long) row.getCell(5).getNumericCellValue());
            }
//            if (row.getCell(6).getCellType() == CellType.STRING) {
//                product.setImage(row.getCell(6).getStringCellValue());
//            }
            if (row.getCell(7).getCellType() == CellType.NUMERIC) {
                long cate_id = (long) row.getCell(7).getNumericCellValue();
                Category category = categoryRepository.findById(cate_id).get();
                product.setCategory(category);
            }
            if (row.getCell(8).getCellType() == CellType.BOOLEAN) {
                product.setActived(row.getCell(8).getBooleanCellValue());
            }
            if (row.getCell(9).getCellType() == CellType.BOOLEAN) {
                product.setDeleted(row.getCell(9).getBooleanCellValue());
            }
//            long category_id =  2;
//            Category category = categoryRepository.findById(category_id).get();
//            product.setCategory(category);
            product.setImage("logo.jpg");
            productRepository.save(product);
        }
        return true;


    }

    private Workbook getWorkBook(MultipartFile file) {
        Workbook workbook = null;
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        try {
            if (extension.equalsIgnoreCase("xlsx")) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else if (extension.equalsIgnoreCase("xls")) {
                workbook = new HSSFWorkbook(file.getInputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }
}
