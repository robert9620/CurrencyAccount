package org.example.service.account;

import lombok.AllArgsConstructor;
import org.example.dto.AccountRequest;
import org.example.dto.AccountResponse;
import org.example.mapper.AccountMapper;
import org.example.model.Account;
import org.example.model.CurrencyType;
import org.example.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
class AccountCreateService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountResponse createAccount(AccountRequest accountRequest) {
        Account account = createAccountModel(accountRequest);

        Account savedAccount = accountRepository.create(account);

        return accountMapper.mapAccountToAccountResponse(savedAccount);
    }

    public Account createAccountModel(AccountRequest accountRequest) {
        Map<CurrencyType, BigDecimal> initialBalances = new HashMap<>();
        initialBalances.put(CurrencyType.PLN, accountRequest.getInitialPLNBalance());
        initialBalances.put(CurrencyType.USD, accountRequest.getInitialUSDBalance());

        return Account.builder()
                .accountId(UUID.randomUUID())
                .firstName(accountRequest.getFirstName())
                .lastName(accountRequest.getLastName())
                .balance(initialBalances)
                .creationDate(LocalDateTime.now())
                .build();
    }
}
