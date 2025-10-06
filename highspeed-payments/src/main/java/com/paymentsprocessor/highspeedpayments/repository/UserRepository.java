package com.paymentsprocessor.highspeedpayments.repository;

import com.paymentsprocessor.highspeedpayments.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsername(String username);
}