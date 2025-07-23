package com.paymentsprocessor.highspeedpayments.service;

import com.paymentsprocessor.highspeedpayments.domain.TransactionRecord;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TransactionHistoryService {

    private final Map<String, TransactionRecord> history = new ConcurrentHashMap<>();

    public void recordTransaction(TransactionRecord record) {
        history.put(record.getTransactionId(), record);
    }

    public Collection<TransactionRecord> getTransactionHistory() {
        return history.values();
    }
}