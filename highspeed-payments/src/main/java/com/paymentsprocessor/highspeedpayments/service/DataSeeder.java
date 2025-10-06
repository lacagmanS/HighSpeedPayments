package com.paymentsprocessor.highspeedpayments.service;

import com.github.javafaker.Faker;
import com.paymentsprocessor.highspeedpayments.domain.AccountEntity;
import com.paymentsprocessor.highspeedpayments.domain.TransactionEntity;
import com.paymentsprocessor.highspeedpayments.domain.UserEntity;
import com.paymentsprocessor.highspeedpayments.repository.AccountRepository;
import com.paymentsprocessor.highspeedpayments.repository.TransactionRepository;
import com.paymentsprocessor.highspeedpayments.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Profile("dev")
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;
    private final Faker faker = new Faker();
    private final Random random = new Random();

    public DataSeeder(UserRepository userRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            return;
        }

        List<UserEntity> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            UserEntity user = new UserEntity();
            user.setUsername(faker.name().username());
            user.setEmail(faker.internet().emailAddress());
            user.setPassword(passwordEncoder.encode("password"));
            user.setCreatedAt(Instant.now());
            user.setRoles("ROLE_USER");
            users.add(user);
        }
        List<UserEntity> savedUsers = userRepository.saveAll(users);

        List<AccountEntity> accounts = new ArrayList<>();
        for (UserEntity user : savedUsers) {
            AccountEntity account = new AccountEntity();
            account.setUser(user);
            account.setBalance(BigDecimal.valueOf(faker.number().randomDouble(2, 1000, 50000)));
            accounts.add(account);
        }
        List<AccountEntity> savedAccounts = accountRepository.saveAll(accounts);

        List<TransactionEntity> transactions = new ArrayList<>();
        for (AccountEntity sourceAccount : savedAccounts) {
            for (int i = 0; i < 200; i++) {
                transactions.add(createRandomTransaction(sourceAccount, savedAccounts));
            }
        }
        transactionRepository.saveAll(transactions);
    }

    private TransactionEntity createRandomTransaction(AccountEntity sourceAccount, List<AccountEntity> allAccounts) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setSourceAccount(sourceAccount);
        
        AccountEntity destinationAccount = allAccounts.get(random.nextInt(allAccounts.size()));
        while(destinationAccount.getId().equals(sourceAccount.getId())) {
            destinationAccount = allAccounts.get(random.nextInt(allAccounts.size()));
        }
        transaction.setDestinationAccount(destinationAccount);
        
        transaction.setAmount(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 500)));
        transaction.setCurrency("GBP");
        transaction.setStatus("COMPLETED");

        Instant randomTimestamp = Instant.now().minus(ThreadLocalRandom.current().nextInt(1, 365), ChronoUnit.DAYS);
        transaction.setTimestamp(randomTimestamp);
        
        transaction.setTransactionType(faker.options().option("P2P_TRANSFER", "ONLINE_PURCHASE", "BILL_PAY"));
        transaction.setMerchantId(transaction.getTransactionType().equals("ONLINE_PURCHASE") ? faker.company().name() : null);
        transaction.setIpAddress(faker.internet().ipV4Address());
        transaction.setUserAgent(faker.internet().userAgentAny());
        transaction.setGeolocation(faker.address().countryCode());
        
        for (int i = 1; i <= 28; i++) {
            setVField(transaction, i, random.nextGaussian());
        }

        return transaction;
    }

    private void setVField(TransactionEntity transaction, int fieldIndex, double value) {
        switch (fieldIndex) {
            case 1: transaction.setV1(value); break;
            case 2: transaction.setV2(value); break;
            case 3: transaction.setV3(value); break;
            case 4: transaction.setV4(value); break;
            case 5: transaction.setV5(value); break;
            case 6: transaction.setV6(value); break;
            case 7: transaction.setV7(value); break;
            case 8: transaction.setV8(value); break;
            case 9: transaction.setV9(value); break;
            case 10: transaction.setV10(value); break;
            case 11: transaction.setV11(value); break;
            case 12: transaction.setV12(value); break;
            case 13: transaction.setV13(value); break;
            case 14: transaction.setV14(value); break;
            case 15: transaction.setV15(value); break;
            case 16: transaction.setV16(value); break;
            case 17: transaction.setV17(value); break;
            case 18: transaction.setV18(value); break;
            case 19: transaction.setV19(value); break;
            case 20: transaction.setV20(value); break;
            case 21: transaction.setV21(value); break;
            case 22: transaction.setV22(value); break;
            case 23: transaction.setV23(value); break;
            case 24: transaction.setV24(value); break;
            case 25: transaction.setV25(value); break;
            case 26: transaction.setV26(value); break;
            case 27: transaction.setV27(value); break;
            case 28: transaction.setV28(value); break;
        }
    }
    @Scheduled(fixedRate = 5000)
    @Transactional
    public void generateLiveTransactions() {
        if (accountRepository.count() < 2) {
            return;
        }

        List<AccountEntity> accounts = accountRepository.findAll();
        List<TransactionEntity> newTransactions = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
             newTransactions.add(createRandomTransaction(accounts.get(random.nextInt(accounts.size())), accounts));
        }
        transactionRepository.saveAll(newTransactions);
    }
}