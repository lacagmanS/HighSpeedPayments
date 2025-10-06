package com.paymentsprocessor.highspeedpayments.service;

import com.paymentsprocessor.highspeedpayments.domain.TransactionEntity;
import com.paymentsprocessor.highspeedpayments.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionHistoryService {

    private final TransactionRepository transactionRepository;

    public TransactionHistoryService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void recordTransaction(TransactionEntity transaction) {
        transactionRepository.save(transaction);
    }

    public Collection<TransactionEntity> getTransactionHistory() {
        return transactionRepository.findAll();
    }
    
    public Optional<TransactionEntity> getTransactionRecord(UUID transactionId) {
        return transactionRepository.findById(transactionId);
    }
}