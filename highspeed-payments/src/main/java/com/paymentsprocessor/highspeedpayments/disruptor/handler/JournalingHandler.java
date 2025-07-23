package com.paymentsprocessor.highspeedpayments.disruptor.handler;

import com.lmax.disruptor.EventHandler;
import com.paymentsprocessor.highspeedpayments.disruptor.event.PaymentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class JournalingHandler implements EventHandler<PaymentEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JournalingHandler.class);
    private final SimpMessagingTemplate messagingTemplate;

    public JournalingHandler(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void onEvent(PaymentEvent event, long sequence, boolean endOfBatch) throws Exception {
        String logMessage = String.format(
                "JOURNAL - Transaction Received: [id=%s, src=%s, dest=%s, amount=%.2f]",
                event.getTransactionId(),
                event.getSourceAccountId(),
                event.getDestinationAccountId(),
                event.getAmount()
        );

        LOGGER.info(logMessage);
        this.messagingTemplate.convertAndSend("/topic/logs", logMessage);
    }
}