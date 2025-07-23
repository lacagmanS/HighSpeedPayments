package com.paymentsprocessor.highspeedpayments.service;

import com.paymentsprocessor.highspeedpayments.disruptor.publisher.PaymentEventPublisher;
import com.paymentsprocessor.highspeedpayments.domain.PaymentRequest; // This class doesn't exist yet, we will create it next
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * A higher-level service that acts as a bridge between the API layer (Controllers)
 * and the core Disruptor-based processing engine.
 */
@Service
public class PaymentService {

    private final PaymentEventPublisher publisher;

    public PaymentService(PaymentEventPublisher publisher) {
        this.publisher = publisher;
    }

    /**
     * Processes an incoming payment request.
     * It performs application-level logic (like generating a transaction ID)
     * and then hands off the data to the publisher to be processed asynchronously.
     *
     * @param request The payment request data from the API.
     * @return The unique transaction ID generated for this payment.
     */
    public String processPayment(PaymentRequest request) {
        // 1. Generate a unique ID for the transaction.
        String transactionId = UUID.randomUUID().toString();

        // 2. Delegate to the publisher to push the event into the Disruptor RingBuffer.
        publisher.publish(
                transactionId,
                request.getAmount(),
                request.getSourceAccountId(),
                request.getDestinationAccountId()
        );

        // 3. Immediately return the transaction ID to the caller.
        // The actual processing happens in the background on the Disruptor's threads.
        return transactionId;
    }
}