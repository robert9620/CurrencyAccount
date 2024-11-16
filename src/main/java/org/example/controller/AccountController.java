package org.example.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.dto.*;
import org.example.service.account.AccountServiceFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
class AccountController {
    private final AccountServiceFacade accountServiceFacade;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody @Valid AccountRequest accountRequest) {
        AccountResponse accountResponse = accountServiceFacade.createAccount(accountRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountResponse);
    }

    @PostMapping("/{accountId}/exchange")
    public ResponseEntity<ExchangeResponse> exchangeCurrency(
            @PathVariable UUID accountId,
            @RequestBody @Valid ExchangeRequest exchangeRequest) {
        ExchangeResponse exchangeResponse = accountServiceFacade.exchangeCurrency(accountId, exchangeRequest);
        return ResponseEntity.ok(exchangeResponse);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDetailsResponse> getAccount(@PathVariable UUID accountId) {
        AccountDetailsResponse accountDetailsResponse = accountServiceFacade.getAccount(accountId);
        return ResponseEntity.ok(accountDetailsResponse);
    }
}
