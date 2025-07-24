package com.paymentsprocessor.highspeedpayments.controller;

import com.paymentsprocessor.highspeedpayments.domain.PaymentRequest;
import com.paymentsprocessor.highspeedpayments.domain.TransactionRecord;
import com.paymentsprocessor.highspeedpayments.service.AccountService;
import com.paymentsprocessor.highspeedpayments.service.CryptoService;
import com.paymentsprocessor.highspeedpayments.service.PaymentService;
import com.paymentsprocessor.highspeedpayments.service.TransactionHistoryService;
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
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    private final PaymentService paymentService;
    private final AccountService accountService;
    private final TransactionHistoryService historyService;
    private final CryptoService cryptoService;

    public PaymentController(PaymentService paymentService, AccountService accountService, TransactionHistoryService historyService, CryptoService cryptoService) {
        this.paymentService = paymentService;
        this.accountService = accountService;
        this.historyService = historyService;
        this.cryptoService = cryptoService;
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

    @GetMapping("/payments/history")
    public ResponseEntity<Collection<TransactionRecord>> getHistory() {
        return ResponseEntity.ok(historyService.getTransactionHistory());
    }

    @PostMapping("/payments/verify")
    public ResponseEntity<Map<String, Boolean>> verifySignature(@RequestBody TransactionRecord record) {
        boolean isValid = false;
        try {
            isValid = cryptoService.verify(record.toSignatureString(), record.getSignature());
        } catch (Exception e) {
            // In a real app, you would log this error.
        }
        return ResponseEntity.ok(Map.of("isValid", isValid));
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