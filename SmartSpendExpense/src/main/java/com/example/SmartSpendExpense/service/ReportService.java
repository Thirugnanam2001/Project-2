package com.example.SmartSpendExpense.service;

import com.example.SmartSpendExpense.model.Expense;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private String safe(Object value) {
        return value == null ? "" : value.toString();
    }

    public void writePdf(List<Expense> expenses, OutputStream out) throws Exception {
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf);

        // 6 columns: Title, Amount, Category, Type, Date, Description
        Table table = new Table(6);

        // Header row
        table.addHeaderCell(new Cell().add(new Paragraph("Title")));
        table.addHeaderCell(new Cell().add(new Paragraph("Amount")));
        table.addHeaderCell(new Cell().add(new Paragraph("Category")));
        table.addHeaderCell(new Cell().add(new Paragraph("Type")));
        table.addHeaderCell(new Cell().add(new Paragraph("Date")));
        table.addHeaderCell(new Cell().add(new Paragraph("Description")));

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        for (Expense e : expenses) {
            table.addCell(safe(e.getTitle()));
            table.addCell(safe(e.getAmount()));
            table.addCell(safe(e.getCategory()));
            table.addCell(safe(e.getType()));
            table.addCell(e.getDate() != null ? e.getDate().format(fmt) : "");
            table.addCell(safe(e.getDescription()));
        }

        doc.add(table);
        doc.close();
    }

    public void writeExcel(List<Expense> expenses, OutputStream out) throws Exception {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Expenses");

            int rowIdx = 0;
            Row header = sheet.createRow(rowIdx++);
            String[] cols = {"Title", "Amount", "Category", "Type", "Date", "Description"};
            for (int i = 0; i < cols.length; i++) {
                header.createCell(i).setCellValue(cols[i]);
            }

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

            for (Expense e : expenses) {
                Row r = sheet.createRow(rowIdx++);
                r.createCell(0).setCellValue(safe(e.getTitle()));
                r.createCell(1).setCellValue(e.getAmount() != null ? e.getAmount().doubleValue() : 0.0);
                r.createCell(2).setCellValue(safe(e.getCategory()));
                r.createCell(3).setCellValue(safe(e.getType()));
                r.createCell(4).setCellValue(e.getDate() != null ? e.getDate().format(fmt) : "");
                r.createCell(5).setCellValue(safe(e.getDescription()));
            }

            for (int i = 0; i < cols.length; i++) {
                sheet.autoSizeColumn(i);
            }
            wb.write(out);
        }
    }
}


//package com.example.SmartSpendExpense.service;
//
//import com.example.SmartSpendExpense.model.Expense;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Cell;
//import com.itextpdf.layout.element.Paragraph;
//import com.itextpdf.layout.element.Table;
//import lombok.RequiredArgsConstructor;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.stereotype.Service;
//
//import java.io.OutputStream;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//
//@Service
//@RequiredArgsConstructor
//public class ReportService {
//
//    public void writePdf(List<Expense> expenses, OutputStream out) throws Exception {
//
//        PdfWriter writer = new PdfWriter(out);
//        PdfDocument pdf = new PdfDocument(writer);
//        Document doc = new Document(pdf);
//
//
//        Table table = new Table(6);
//        table.addHeaderCell(new Cell().add(new Paragraph("Title")));
//        table.addHeaderCell(new Cell().add(new Paragraph("Amount")));
//        table.addHeaderCell(new Cell().add(new Paragraph("Category")));
//        table.addHeaderCell(new Cell().add(new Paragraph("Type")));
//        table.addHeaderCell(new Cell().add(new Paragraph("Date")));
//        table.addHeaderCell(new Cell().add(new Paragraph("Description")));
//
//
//        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
//
//        for (Expense e : expenses) {
//            table.addCell(e.getTitle());
//            table.addCell(String.valueOf(e.getAmount()));
//            table.addCell(e.getCategory());
//            table.addCell(e.getType());
//            table.addCell(e.getDate() != null ? e.getDate().format(fmt) : "");
//            table.addCell(e.getDescription() != null ? e.getDescription() : "");
//        }
//
//        doc.add(table);
//        doc.close();
//    }
//
//    public void writeExcel(List<Expense> expenses, OutputStream out) throws Exception {
//        try (Workbook wb = new XSSFWorkbook()) {
//            Sheet sheet = wb.createSheet("Expenses");
//
//            int rowIdx = 0;
//            Row header = sheet.createRow(rowIdx++);
//            String[] cols = {"Title", "Amount", "Category", "Type", "Date", "Description"};
//            for (int i = 0; i < cols.length; i++) header.createCell(i).setCellValue(cols[i]);
//
//            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
//
//            for (Expense e : expenses) {
//                Row r = sheet.createRow(rowIdx++);
//                r.createCell(0).setCellValue(e.getTitle());
//                r.createCell(1).setCellValue(e.getAmount() != null ? e.getAmount().doubleValue() : 0.0);
//                r.createCell(2).setCellValue(e.getCategory());
//                r.createCell(3).setCellValue(e.getType());
//                r.createCell(4).setCellValue(e.getDate() != null ? e.getDate().format(fmt) : "");
//                r.createCell(5).setCellValue(e.getDescription() != null ? e.getDescription() : "");
//            }
//
//            for (int i = 0; i < cols.length; i++) sheet.autoSizeColumn(i);
//            wb.write(out);
//        }
//    }
//}