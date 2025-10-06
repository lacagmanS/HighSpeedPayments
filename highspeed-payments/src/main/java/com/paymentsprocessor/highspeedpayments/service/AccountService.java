package com.paymentsprocessor.highspeedpayments.service;

import com.paymentsprocessor.highspeedpayments.domain.AccountEntity;
import com.paymentsprocessor.highspeedpayments.domain.UserEntity;
import com.paymentsprocessor.highspeedpayments.repository.AccountRepository;
import com.paymentsprocessor.highspeedpayments.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public void createAccount(UUID userId, BigDecimal initialBalance) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        AccountEntity newAccount = new AccountEntity();
        newAccount.setUser(user);
        newAccount.setBalance(initialBalance);
        accountRepository.save(newAccount);
    }

    public BigDecimal getBalance(UUID accountId) {
        return accountRepository.findById(accountId)
            .map(AccountEntity::getBalance)
            .orElse(BigDecimal.ZERO);
    }
    
    public List<AccountEntity> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Transactional
    public void settlePayment(UUID sourceAccountId, UUID destinationAccountId, BigDecimal amount) {
        AccountEntity sourceAccount = accountRepository.findById(sourceAccountId)
            .orElseThrow(() -> new RuntimeException("Source account not found"));
        
        AccountEntity destinationAccount = accountRepository.findById(destinationAccountId)
            .orElseThrow(() -> new RuntimeException("Destination account not found"));
        
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
        destinationAccount.setBalance(destinationAccount.getBalance().add(amount));

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);
    }
}