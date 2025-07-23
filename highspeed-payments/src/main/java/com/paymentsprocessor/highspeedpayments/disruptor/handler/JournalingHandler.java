package com.paymentsprocessor.highspeedpayments.disruptor.handler;

import com.lmax.disruptor.EventHandler;
import com.paymentsprocessor.highspeedpayments.disruptor.event.PaymentEvent;
import com.paymentsprocessor.highspeedpayments.service.LoggingService;
import org.springframework.stereotype.Component;

/**
 * This is the first handler in our processing pipeline.
 *
 * Its SOLE RESPONSIBILITY is to create a persistent record of the incoming event
 * before any business logic is applied. This is for durability and auditing.
 * It reads the data from the PaymentEvent and passes it to a dedicated service for persistence.
 *
 * The @Component annotation tells Spring to manage this class as a bean.
 */
@Component
public class JournalingHandler implements EventHandler<PaymentEvent> {

    private final LoggingService loggingService;

    /**
     * Constructor-based dependency injection. Spring will automatically provide
     * an instance of LoggingService when it creates this JournalingHandler.
     * This is a modern, clean way to manage dependencies.
     *
     * @param loggingService The service responsible for the actual logging logic.
     */
    public JournalingHandler(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    /**
     * This method is called by the Disruptor framework when an event is available.
     *
     * @param event The event published to the RingBuffer.
     * @param sequence The sequence number of the event in the RingBuffer.
     * @param endOfBatch True if this is the last event in a batch from the RingBuffer.
     * @throws Exception Can be thrown if there's an error during processing.
     */
    @Override
    public void onEvent(PaymentEvent event, long sequence, boolean endOfBatch) throws Exception {
        // Delegate the actual work to our specialized service.
        loggingService.journal(event);
    }
}