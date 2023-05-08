package com.dqt.ecommerce.import_export;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.dqt.ecommerce.model.Product;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ProductExportExcel {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Product> listProducts;

    public ProductExportExcel(List<Product> listProducts) {
        this.listProducts = listProducts;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Products");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Stt", style);
        createCell(row, 1, "Name", style);
        createCell(row, 2, "Price", style);
        createCell(row, 3, "Title", style);
        createCell(row, 4, "Sale", style);
        createCell(row, 5, "Cost", style);
        createCell(row, 6, "Image", style);
        createCell(row, 7, "Category", style);
        createCell(row, 8, "Actived", style);
        createCell(row, 9, "Deleted", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Float) {
            cell.setCellValue((Float) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Product product : listProducts) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, product.getId(), style);
            createCell(row, columnCount++, product.getName(), style);
            createCell(row, columnCount++, product.getPrice(), style);
            createCell(row, columnCount++, product.getTitle(), style);
            createCell(row, columnCount++, product.getSale(), style);
            createCell(row, columnCount++, product.getCost(), style);
            createCell(row, columnCount++, product.getImage(), style);
            createCell(row, columnCount++, product.getCategory().getName(), style);
            createCell(row, columnCount++, product.isActived() ? "Actived" : "Not Actived", style);
            createCell(row, columnCount++, product.isDeleted() ? "Deleted" : "Not Deleted", style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
