package com.paymentsprocessor.highspeedpayments.disruptor.handler;

import com.lmax.disruptor.EventHandler;
import com.paymentsprocessor.highspeedpayments.disruptor.event.PaymentEvent;
import com.paymentsprocessor.highspeedpayments.domain.TransactionRecord;
import com.paymentsprocessor.highspeedpayments.service.AccountService;
import com.paymentsprocessor.highspeedpayments.service.CryptoService;
import com.paymentsprocessor.highspeedpayments.service.MetricsService;
import com.paymentsprocessor.highspeedpayments.service.TransactionHistoryService;
import org.springframework.stereotype.Component;

@Component
public class SettlementHandler implements EventHandler<PaymentEvent> {

    private final AccountService accountService;
    private final MetricsService metricsService;
    private final TransactionHistoryService historyService;
    private final CryptoService cryptoService;

    public SettlementHandler(AccountService accountService, MetricsService metricsService, TransactionHistoryService historyService, CryptoService cryptoService) {
        this.accountService = accountService;
        this.metricsService = metricsService;
        this.historyService = historyService;
        this.cryptoService = cryptoService;
    }

    @Override
    public void onEvent(PaymentEvent event, long sequence, boolean endOfBatch) throws Exception {
        String finalStatus = "FAILED";
        if ("VALIDATED".equals(event.getStatus())) {
            accountService.settlePayment(
                event.getSourceAccountId(),
                event.getDestinationAccountId(),
                event.getAmount()
            );
            finalStatus = "COMPLETED";
            event.setStatus(finalStatus);

            long latencyNanos = System.nanoTime() - event.getStartTime();
            metricsService.recordTransaction(latencyNanos);
        } else {
            finalStatus = event.getStatus();
        }

        TransactionRecord record = new TransactionRecord(
                event.getTransactionId(),
                event.getSourceAccountId(),
                event.getDestinationAccountId(),
                event.getAmount(),
                finalStatus
        );

        String signature = cryptoService.sign(record.toSignatureString());
        record.setSignature(signature);
        historyService.recordTransaction(record);
    }
}