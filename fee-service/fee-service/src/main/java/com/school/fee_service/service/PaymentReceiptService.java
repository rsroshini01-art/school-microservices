package com.school.fee_service.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.school.fee_service.client.StudentClient;
import com.school.fee_service.entity.FeePayment;
import com.school.fee_service.repository.FeePaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
public class PaymentReceiptService {

    @Autowired
    private FeePaymentRepository feePaymentRepository;

    @Autowired
    private StudentClient studentClient;

    // For the receipt we only read the name. If student-service is down, we show the id instead.
    @SuppressWarnings("unchecked")
    private String findStudentLabel(Long studentId) {
        try {
            Map<String, Object> body = studentClient.getStudent(studentId);
            Map<String, Object> data = (Map<String, Object>) body.get("data");
            return data.get("firstName") + " " + data.get("lastName");
        } catch (Exception e) {
            return "Student ID " + studentId;
        }
    }

    public byte[] generateReceipt(Long paymentId) {
        FeePayment payment = feePaymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentId));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Payment Receipt")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(20));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Receipt No: RCPT-" + payment.getId()));
            document.add(new Paragraph("Student: " + findStudentLabel(payment.getStudentId())));
            document.add(new Paragraph("Fee: " + payment.getFeeStructure().getFeeName()));
            document.add(new Paragraph("Amount Paid: " + payment.getAmountPaid()));
            document.add(new Paragraph("Payment Date: " + payment.getPaymentDate()));
            document.add(new Paragraph("Payment Method: " + (payment.getPaymentMethod() != null ? payment.getPaymentMethod() : "N/A")));
            document.add(new Paragraph("Transaction Ref: " + (payment.getTransactionReference() != null ? payment.getTransactionReference() : "N/A")));

            double balance = payment.getFeeStructure().getAmount() - payment.getAmountPaid();
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Remaining Balance: " + balance).setBold());

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate receipt: " + e.getMessage());
        }

        return outputStream.toByteArray();
    }
}