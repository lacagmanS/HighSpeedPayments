package com.paymentsprocessor.highspeedpayments.disruptor.publisher;

import com.lmax.disruptor.RingBuffer;
import com.paymentsprocessor.highspeedpayments.disruptor.event.PaymentEvent;
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
        // 1. Claim the next available sequence in the RingBuffer.
        // This is a blocking call if the buffer is full.
        long sequence = ringBuffer.next();
        try {
            // 2. Get the pre-allocated event object from the slot for this sequence.
            PaymentEvent event = ringBuffer.get(sequence);

            // 3. Populate the event with the data for this specific payment.
            event.setTransactionId(transactionId);
            event.setAmount(amount);
            event.setSourceAccountId(sourceAccountId);
            event.setDestinationAccountId(destinationAccountId);
            // The 'status' field is initially null, to be set by the handlers.

        } finally {
            // 4. Publish the sequence. This makes the event available to the handlers.
            // This MUST be in a 'finally' block to ensure that the sequence is always
            // published, even if an error occurs during event population. Failure to
            // do so would cause the Disruptor to halt.
            ringBuffer.publish(sequence);
        }
    }
}