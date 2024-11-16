package org.example.service.account;

import lombok.AllArgsConstructor;
import org.example.dto.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountServiceFacade {
    private final AccountCreateService accountCreateService;
    private final AccountExchangeCurrencyService accountExchangeCurrencyService;
    private final AccountDetailsService accountDetailsService;

    public AccountResponse createAccount(AccountRequest accountRequest) {
        return accountCreateService.createAccount(accountRequest);
    }

    public ExchangeResponse exchangeCurrency(UUID accountId, ExchangeRequest exchangeRequest) {
        return accountExchangeCurrencyService.exchangeCurrency(accountId, exchangeRequest);
    }

    public AccountDetailsResponse getAccount(UUID accountId) {
        return accountDetailsService.getAccount(accountId);
    }
}
