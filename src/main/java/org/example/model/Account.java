package org.example.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
public class Account {
    private UUID accountId;
    private String firstName;
    private String lastName;
    private Map<CurrencyType, BigDecimal> balance;
    private LocalDateTime creationDate;

    public void setBalance(CurrencyType currencyType, BigDecimal balance) {
        if (currencyType != null && balance != null) {
            this.balance.put(currencyType, balance);
        } else {
            throw new IllegalArgumentException("CurrencyType and amount cannot be null");
        }
    }
}
