package com.school.exam_service.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.school.exam_service.dto.MarksResponse;
import com.school.exam_service.dto.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class ReportCardService {

    @Autowired
    private ResultService resultService;

    public byte[] generateReportCard(Long studentId, Long examId) {
        ResultResponse result = resultService.getResult(studentId, examId);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Report Card")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(20));

            document.add(new Paragraph("Student: " + result.getStudentName()));
            document.add(new Paragraph("Exam: " + result.getExamName()));
            document.add(new Paragraph(" "));

            Table table = new Table(UnitValue.createPercentArray(new float[]{3, 2, 2, 2}));
            table.setWidth(UnitValue.createPercentValue(100));

            table.addHeaderCell(new Cell().add(new Paragraph("Subject")));
            table.addHeaderCell(new Cell().add(new Paragraph("Marks Obtained")));
            table.addHeaderCell(new Cell().add(new Paragraph("Max Marks")));
            table.addHeaderCell(new Cell().add(new Paragraph("Grade")));

            for (MarksResponse subject : result.getSubjects()) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(subject.getSubjectName()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(subject.getMarksObtained()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(subject.getMaxMarks()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(subject.getGrade()))));
            }

            document.add(table);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Total Marks: " + result.getTotalMarksObtained() + " / " + result.getTotalMaxMarks()));
            document.add(new Paragraph("Percentage: " + result.getPercentage() + "%"));
            document.add(new Paragraph("Overall Grade: " + result.getGrade()));
            document.add(new Paragraph("Result: " + result.getResult())
                    .setBold()
                    .setFontSize(14));

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate report card: " + e.getMessage());
        }

        return outputStream.toByteArray();
    }
}