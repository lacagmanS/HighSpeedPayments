package com.paymentsprocessor.highspeedpayments.controller;

import com.paymentsprocessor.highspeedpayments.domain.PaymentRequest;
import com.paymentsprocessor.highspeedpayments.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * The REST API Controller for handling payment-related HTTP requests.
 * This is the "front door" to our application.
 */
@RestController
@RequestMapping("/api/payments") // All endpoints in this controller will start with /api/payments
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Endpoint for submitting a new payment.
     * It listens for POST requests to /api/payments.
     *
     * @param paymentRequest The request body, automatically deserialized from JSON into our PaymentRequest DTO.
     * The @Valid annotation triggers the validation rules we defined in that class.
     * @return An HTTP response.
     */
    @PostMapping
    public ResponseEntity<Map<String, String>> submitPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        // Delegate the core logic to the service layer.
        String transactionId = paymentService.processPayment(paymentRequest);

        // This is the classic asynchronous response. We don't wait for the payment to be fully
        // processed. We immediately tell the client that we have ACCEPTED their request
        // and give them a transaction ID to track it.
        // The HTTP 202 Accepted status is perfect for this scenario.
        Map<String, String> response = Map.of(
                "message", "Payment request accepted for processing.",
                "transactionId", transactionId
        );

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}