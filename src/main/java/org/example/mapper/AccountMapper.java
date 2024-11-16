package org.example.mapper;

import lombok.AllArgsConstructor;
import org.example.dto.AccountDetailsResponse;
import org.example.dto.AccountResponse;
import org.example.dto.ExchangeResponse;
import org.example.model.Account;
import org.example.model.CurrencyType;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AccountMapper {
    public AccountDetailsResponse mapAccountToAccountDetailsResponse(Account account) {
        return AccountDetailsResponse.builder()
                .accountId(account.getAccountId())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .balancePLN(account.getBalance().get(CurrencyType.PLN))
                .balanceUSD(account.getBalance().get(CurrencyType.USD))
                .creationDate(account.getCreationDate())
                .build();
    }

    public AccountResponse mapAccountToAccountResponse(Account account) {
        return AccountResponse.builder()
                .accountId(account.getAccountId())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .balancePLN(account.getBalance().get(CurrencyType.PLN))
                .balanceUSD(account.getBalance().get(CurrencyType.USD))
                .creationDate(account.getCreationDate())
                .build();
    }

    public ExchangeResponse mapAccountToExchangeResponse(Account account) {
        return ExchangeResponse.builder()
                .accountId(account.getAccountId())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .balancePLN(account.getBalance().get(CurrencyType.PLN))
                .balanceUSD(account.getBalance().get(CurrencyType.USD))
                .creationDate(account.getCreationDate())
                .build();
    }
}
