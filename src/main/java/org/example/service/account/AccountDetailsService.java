package org.example.service.account;

import lombok.AllArgsConstructor;
import org.example.dto.AccountDetailsResponse;
import org.example.exception.AccountNotFoundException;
import org.example.mapper.AccountMapper;
import org.example.model.Account;
import org.example.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
class AccountDetailsService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountDetailsResponse getAccount(UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        return accountMapper.mapAccountToAccountDetailsResponse(account);
    }
}
