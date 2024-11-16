package org.example.repository;

import org.example.exception.AccountNotFoundException;
import org.example.model.Account;
import org.example.model.CurrencyType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository("accountRepositoryImpl")
public class AccountRepositoryImpl implements AccountRepository{
    private final Map<UUID, Account> accountMap = new HashMap<>();

    public AccountRepositoryImpl() {
        accountMap.put(UUID.fromString("cbea8882-677d-4a0d-8e07-e9a25a2b5e46"),
                Account.builder()
                        .accountId(UUID.fromString("cbea8882-677d-4a0d-8e07-e9a25a2b5e46"))
                        .firstName("J")
                        .lastName("K")
                        .balance(new HashMap<>(Map.of(
                                CurrencyType.PLN, new BigDecimal("100"),
                                CurrencyType.USD, new BigDecimal("100")
                        )))
                        .creationDate(LocalDateTime.now())
                        .build()
        );

        accountMap.put(UUID.fromString("5dcd21a1-cf78-4b65-809e-2ba4fc26f8c7"),
                Account.builder()
                        .accountId(UUID.fromString("5dcd21a1-cf78-4b65-809e-2ba4fc26f8c7"))
                        .firstName("R")
                        .lastName("T")
                        .balance(new HashMap<>(Map.of(
                                CurrencyType.PLN, new BigDecimal("200"),
                                CurrencyType.USD, new BigDecimal("300")
                        )))
                        .creationDate(LocalDateTime.now())
                        .build()
        );

        accountMap.put(UUID.fromString("5aa7a213-4595-4af1-a3b1-89f9a3244c52"),
                Account.builder()
                        .accountId(UUID.fromString("5aa7a213-4595-4af1-a3b1-89f9a3244c52"))
                        .firstName("A")
                        .lastName("T")
                        .balance(new HashMap<>(Map.of(
                                CurrencyType.PLN, new BigDecimal("1000"),
                                CurrencyType.USD, new BigDecimal("1000")
                        )))
                        .creationDate(LocalDateTime.now())
                        .build()
        );

        accountMap.put(UUID.fromString("e75498fe-7a56-4b2f-bf7f-67dba50a08f6"),
                Account.builder()
                        .accountId(UUID.fromString("e75498fe-7a56-4b2f-bf7f-67dba50a08f6"))
                        .firstName("K")
                        .lastName("T")
                        .balance(new HashMap<>(Map.of(
                                CurrencyType.PLN, new BigDecimal("1"),
                                CurrencyType.USD, new BigDecimal("10")
                        )))
                        .creationDate(LocalDateTime.now())
                        .build()
        );
    }

    @Override
    public Account create(Account account) {
        accountMap.put(account.getAccountId(), account);
        return account;
    }

    @Override
    public void updateBalance(UUID accountId, CurrencyType currencyToUpdate, BigDecimal newBalance) {
        Account account = accountMap.get(accountId);
        if (account == null) {
            throw new AccountNotFoundException("Account with ID " + accountId + " not found.");
        }
        account.setBalance(currencyToUpdate, newBalance);
    }

    @Override
    public Optional<Account> findById(UUID accountId) {
        return Optional.ofNullable(accountMap.get(accountId));
    }
}
