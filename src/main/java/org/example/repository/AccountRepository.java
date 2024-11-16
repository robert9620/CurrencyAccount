package org.example.repository;

import org.example.model.Account;
import org.example.model.CurrencyType;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
    Account create(Account account);

    void updateBalance(UUID accountId, CurrencyType currencyToUpdate, BigDecimal newBalance);

    Optional<Account> findById(UUID accountId);
}
