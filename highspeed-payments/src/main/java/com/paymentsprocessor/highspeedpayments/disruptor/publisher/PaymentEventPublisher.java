package com.paymentsprocessor.highspeedpayments.disruptor.publisher;

import com.lmax.disruptor.RingBuffer;
import com.paymentsprocessor.highspeedpayments.disruptor.event.PaymentEvent;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * The PaymentEventPublisher is responsible for publishing events into the RingBuffer.
 * It encapsulates the low-level mechanics of the Disruptor's publishing process,
 * providing a clean and simple interface for other parts of the application.
 *
 * This component is a singleton managed by Spring (@Component)
 */
@Component
public class PaymentEventPublisher {

    private final RingBuffer<PaymentEvent> ringBuffer;

    /**
     * The publisher is initialized with the RingBuffer bean that we created in DisruptorConfig.
     * Spring handles this injection automatically.
     *
     * @param ringBuffer The Disruptor RingBuffer.
     */
    public PaymentEventPublisher(RingBuffer<PaymentEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * The core publishing method. It takes the raw payment data, claims a slot
     * in the RingBuffer, populates the event in that slot, and then publishes it.
     *
     * @param transactionId The unique ID for this transaction.
     * @param amount The monetary value of the transaction.
     * @param sourceAccountId The source account.
     * @param destinationAccountId The destination account.
     */
    public void publish(String transactionId, BigDecimal amount, String sourceAccountId, String destinationAccountId) {
    long sequence = ringBuffer.next();
    try {
        PaymentEvent event = ringBuffer.get(sequence);

        event.setTransactionId(transactionId);
        event.setAmount(amount);
        event.setSourceAccountId(sourceAccountId);
        event.setDestinationAccountId(destinationAccountId);
        event.setStartTime(System.nanoTime());

    } finally {
        ringBuffer.publish(sequence);
    }
}
}