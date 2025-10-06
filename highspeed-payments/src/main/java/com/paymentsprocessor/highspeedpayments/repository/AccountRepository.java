package com.paymentsprocessor.highspeedpayments.repository;

import com.paymentsprocessor.highspeedpayments.domain.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
}