package com.paymentsprocessor.highspeedpayments.disruptor.handler;

import com.lmax.disruptor.EventHandler;
import com.paymentsprocessor.highspeedpayments.disruptor.event.PaymentEvent;
import com.paymentsprocessor.highspeedpayments.service.LoggingService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * This is the second handler in our processing pipeline.
 *
 * Its responsibility is to apply business rules to the incoming payment.
 * This is where you would check for things like sufficient funds, valid accounts,
 * transaction limits, etc. For this project, we will perform a simple validation
 * to check if the payment amount is positive.
 */
@Component
public class ValidationHandler implements EventHandler<PaymentEvent> {

    private final LoggingService loggingService;

    public ValidationHandler(LoggingService loggingService) {
        // We can also use our logging service here to report validation outcomes.
        this.loggingService = loggingService;
    }

    @Override
    public void onEvent(PaymentEvent event, long sequence, boolean endOfBatch) throws Exception {
        // In a real application, you would have a separate "Status" enum.
        // For simplicity, we will use simple strings.

        // Rule #1: The payment amount must be greater than zero.
        if (event.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            // If validation passes, we update the status on the event itself.
            // The next handler in the chain can then act on this status.
            event.setStatus("VALIDATED");
        } else {
            event.setStatus("FAILED_INVALID_AMOUNT");
        }

        // It's good practice to log the outcome of business-critical steps.
        // We'll add this logging logic after we update the PaymentEvent in Step 3.
    }
}