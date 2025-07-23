package com.paymentsprocessor.highspeedpayments.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AccountService {

    private final Map<String, BigDecimal> accountBalances = new ConcurrentHashMap<>();

    public void createAccount(String accountId, BigDecimal initialBalance) {
        accountBalances.putIfAbsent(accountId, initialBalance);
    }

    public BigDecimal getBalance(String accountId) {
        return accountBalances.getOrDefault(accountId, BigDecimal.ZERO);
    }

    public Map<String, BigDecimal> getAllAccounts() {
        return accountBalances;
    }
}