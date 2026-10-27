package com.school.fee_service.controller;

import com.school.fee_service.service.PaymentReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fee-payments")
public class PaymentReceiptController {

    @Autowired
    private PaymentReceiptService paymentReceiptService;

    @GetMapping("/{id}/receipt")
    public ResponseEntity<byte[]> getReceipt(@PathVariable Long id) {
        byte[] pdfBytes = paymentReceiptService.generateReceipt(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "receipt-" + id + ".pdf");

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}