package com.paymentsprocessor.highspeedpayments.disruptor.handler;

import com.lmax.disruptor.EventHandler;
import com.paymentsprocessor.highspeedpayments.disruptor.event.PaymentEvent;
import org.springframework.stereotype.Component;

/**
 * This is the FINAL handler in our processing pipeline.
 *
 * Its sole, critical responsibility is to clean the event object after it has been
 * fully processed. This prevents "stale" data from being accidentally read by a
 * future operation that reuses this slot in the RingBuffer.
 *
 * This handler ensures the integrity of the event reuse mechanism.
 */
@Component
public class ClearingHandler implements EventHandler<PaymentEvent> {

    @Override
    public void onEvent(PaymentEvent event, long sequence, boolean endOfBatch) throws Exception {
        // Call the clear() method on the event to reset its fields.
        event.clear();
    }
}