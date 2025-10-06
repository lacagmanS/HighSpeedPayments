package com.paymentsprocessor.highspeedpayments.disruptor.handler;

import com.lmax.disruptor.EventHandler;
import com.paymentsprocessor.highspeedpayments.disruptor.event.PaymentEvent;
import com.paymentsprocessor.highspeedpayments.domain.AccountEntity;
import com.paymentsprocessor.highspeedpayments.domain.TransactionEntity;
import com.paymentsprocessor.highspeedpayments.repository.AccountRepository;
import com.paymentsprocessor.highspeedpayments.service.MetricsService;
import com.paymentsprocessor.highspeedpayments.service.TransactionHistoryService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class SettlementHandler implements EventHandler<PaymentEvent> {

    private final AccountRepository accountRepository;
    private final MetricsService metricsService;
    private final TransactionHistoryService historyService;

    public SettlementHandler(AccountRepository accountRepository, MetricsService metricsService, TransactionHistoryService historyService) {
        this.accountRepository = accountRepository;
        this.metricsService = metricsService;
        this.historyService = historyService;
    }

    @Override
    public void onEvent(PaymentEvent event, long sequence, boolean endOfBatch) throws Exception {
        String finalStatus;

        if ("VALIDATED".equals(event.getStatus())) {
            UUID sourceId = UUID.fromString(event.getSourceAccountId());
            UUID destId = UUID.fromString(event.getDestinationAccountId());

            AccountEntity sourceAccount = accountRepository.findById(sourceId).orElse(null);
            AccountEntity destAccount = accountRepository.findById(destId).orElse(null);

            if (sourceAccount != null && destAccount != null) {
                sourceAccount.setBalance(sourceAccount.getBalance().subtract(event.getAmount()));
                destAccount.setBalance(destAccount.getBalance().add(event.getAmount()));
                accountRepository.save(sourceAccount);
                accountRepository.save(destAccount);
                finalStatus = "COMPLETED";
                
                long latencyNanos = System.nanoTime() - event.getStartTime();
                metricsService.recordTransaction(latencyNanos);
            } else {
                finalStatus = "FAILED_ACCOUNT_NOT_FOUND";
            }
        } else {
            finalStatus = event.getStatus();
        }

        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(UUID.fromString(event.getTransactionId()));
        transaction.setAmount(event.getAmount());
        transaction.setStatus(finalStatus);
        transaction.setTimestamp(Instant.now());
        transaction.setCurrency("GBP");
        transaction.setTransactionType("P2P_TRANSFER");
        
        AccountEntity source = new AccountEntity();
        source.setId(UUID.fromString(event.getSourceAccountId()));
        transaction.setSourceAccount(source);

        AccountEntity dest = new AccountEntity();
        dest.setId(UUID.fromString(event.getDestinationAccountId()));
        transaction.setDestinationAccount(dest);
        
    }
}