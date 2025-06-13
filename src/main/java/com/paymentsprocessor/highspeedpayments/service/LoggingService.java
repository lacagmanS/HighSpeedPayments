package com.paymentsprocessor.highspeedpayments.service;

import com.paymentsprocessor.highspeedpayments.disruptor.event.PaymentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * A simple service dedicated to logging application events.
 * In a real-world application, this might write to a structured log file (e.g., JSON),
 * a database, or a log aggregation service like Splunk or ELK.
 *
 * The @Service annotation tells Spring to manage this class as a bean,
 * allowing it to be easily injected into other components.
 */
@Service
public class LoggingService {

    // SLF4J is a standard logging facade in the Java world. Spring Boot pre-configures it.
    // We create a logger instance specific to this class.
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingService.class);

    /**
     * Logs the details of a PaymentEvent. This simulates writing to a journal.
     * @param event The PaymentEvent to be logged.
     */
    public void journal(PaymentEvent event) {
        // In a real system, you might format this as JSON for easier parsing.
        // For our project, a clear, readable string is perfect.
        String logMessage = String.format(
                "JOURNAL - Transaction Received: [id=%s, src=%s, dest=%s, amount=%.2f]",
                event.getTransactionId(),
                event.getSourceAccountId(),
                event.getDestinationAccountId(),
                event.getAmount()
        );

        LOGGER.info(logMessage);
    }
}