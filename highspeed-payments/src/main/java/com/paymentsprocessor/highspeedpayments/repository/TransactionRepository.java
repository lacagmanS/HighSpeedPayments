package com.paymentsprocessor.highspeedpayments.repository;

import com.paymentsprocessor.highspeedpayments.domain.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {
}