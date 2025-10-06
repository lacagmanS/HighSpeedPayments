package com.paymentsprocessor.highspeedpayments.controller;

import com.paymentsprocessor.highspeedpayments.domain.AccountEntity;
import com.paymentsprocessor.highspeedpayments.domain.PaymentRequest;
import com.paymentsprocessor.highspeedpayments.domain.TransactionEntity;
import com.paymentsprocessor.highspeedpayments.service.AccountService;
import com.paymentsprocessor.highspeedpayments.service.PaymentService;
import com.paymentsprocessor.highspeedpayments.service.TransactionHistoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    private final PaymentService paymentService;
    private final AccountService accountService;
    private final TransactionHistoryService historyService;

    public PaymentController(PaymentService paymentService, AccountService accountService, TransactionHistoryService historyService) {
        this.paymentService = paymentService;
        this.accountService = accountService;
        this.historyService = historyService;
    }

    @PostMapping("/accounts")
    public ResponseEntity<Void> createAccount(@RequestBody Map<String, String> payload) {
        UUID userId = UUID.fromString(payload.get("userId"));
        BigDecimal balance = new BigDecimal(payload.get("balance"));
        accountService.createAccount(userId, balance);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountEntity>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/payments/history")
    public ResponseEntity<Collection<TransactionEntity>> getHistory() {
        return ResponseEntity.ok(historyService.getTransactionHistory());
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