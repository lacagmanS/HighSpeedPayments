package com.paymentsprocessor.highspeedpayments.disruptor.handler;

import com.lmax.disruptor.EventHandler;
import com.paymentsprocessor.highspeedpayments.disruptor.event.PaymentEvent;
import com.paymentsprocessor.highspeedpayments.domain.AccountEntity;
import com.paymentsprocessor.highspeedpayments.repository.AccountRepository;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
public class ValidationHandler implements EventHandler<PaymentEvent> {

    private final AccountRepository accountRepository;

    public ValidationHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void onEvent(PaymentEvent event, long sequence, boolean endOfBatch) throws Exception {
        UUID sourceAccountId = UUID.fromString(event.getSourceAccountId());
        Optional<AccountEntity> sourceAccountOpt = accountRepository.findById(sourceAccountId);

        if (sourceAccountOpt.isEmpty()) {
            event.setStatus("FAILED_INVALID_SOURCE_ACCOUNT");
            return;
        }

        AccountEntity sourceAccount = sourceAccountOpt.get();
        boolean isAmountPositive = event.getAmount().compareTo(BigDecimal.ZERO) > 0;
        boolean hasSufficientFunds = sourceAccount.getBalance().compareTo(event.getAmount()) >= 0;

        if (isAmountPositive && hasSufficientFunds) {
            event.setStatus("VALIDATED");
        } else {
            event.setStatus("FAILED_INSUFFICIENT_FUNDS");
        }
    }
}