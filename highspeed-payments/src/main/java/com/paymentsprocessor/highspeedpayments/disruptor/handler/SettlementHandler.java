package com.paymentsprocessor.highspeedpayments.disruptor.handler;

import com.lmax.disruptor.EventHandler;
import com.paymentsprocessor.highspeedpayments.disruptor.event.PaymentEvent;
import com.paymentsprocessor.highspeedpayments.service.AccountService;
import org.springframework.stereotype.Component;

@Component
public class SettlementHandler implements EventHandler<PaymentEvent> {

    private final AccountService accountService;

    public SettlementHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void onEvent(PaymentEvent event, long sequence, boolean endOfBatch) throws Exception {
        if ("VALIDATED".equals(event.getStatus())) {
            accountService.settlePayment(
                event.getSourceAccountId(),
                event.getDestinationAccountId(),
                event.getAmount()
            );
            event.setStatus("COMPLETED");
        }
    }
}