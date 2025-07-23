package com.paymentsprocessor.highspeedpayments.controller;

import com.paymentsprocessor.highspeedpayments.domain.PaymentRequest;
import com.paymentsprocessor.highspeedpayments.service.AccountService;
import com.paymentsprocessor.highspeedpayments.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    private final PaymentService paymentService;
    private final AccountService accountService;

    public PaymentController(PaymentService paymentService, AccountService accountService) {
        this.paymentService = paymentService;
        this.accountService = accountService;
    }

    @PostMapping("/accounts")
    public ResponseEntity<Void> createAccount(@RequestBody Map<String, String> payload) {
        String accountId = payload.get("accountId");
        BigDecimal balance = new BigDecimal(payload.get("balance"));
        accountService.createAccount(accountId, balance);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/accounts")
    public ResponseEntity<Map<String, BigDecimal>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @PostMapping("/payments")
    public ResponseEntity<Map<String, String>> submitPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        String transactionId = paymentService.processPayment(paymentRequest);
        Map<String, String> response = Map.of(
                "message", "Payment request accepted for processing.",
                "transactionId", transactionId
        );
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}