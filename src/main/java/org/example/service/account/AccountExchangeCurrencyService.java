package org.example.service.account;

import lombok.AllArgsConstructor;
import org.example.dto.ExchangeRequest;
import org.example.dto.ExchangeResponse;
import org.example.exception.AccountNotFoundException;
import org.example.exception.InsufficientFundsException;
import org.example.mapper.AccountMapper;
import org.example.model.Account;
import org.example.model.CurrencyType;
import org.example.repository.AccountRepository;
import org.example.service.ExchangeRateService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Service
@AllArgsConstructor
class AccountExchangeCurrencyService {
    private final AccountRepository accountRepository;
    private final ExchangeRateService exchangeRateService;
    private final AccountMapper accountMapper;

    public ExchangeResponse exchangeCurrency(UUID accountId, ExchangeRequest exchangeRequest) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        BigDecimal amountToExchange = exchangeRequest.getAmountToExchange();

        return switch (exchangeRequest.getCurrencyFrom() + "->" + exchangeRequest.getCurrencyTo()) {
            case "PLN->USD" -> exchangeFromPlnToUsd(account, amountToExchange);
            case "USD->PLN" -> exchangeFromUsdToPln(account, amountToExchange);
            default -> throw new IllegalArgumentException("Invalid currency pair");
        };
    }

    private ExchangeResponse exchangeFromPlnToUsd(Account account, BigDecimal amountToExchange) {
        BigDecimal plnBalance = account.getBalance().get(CurrencyType.PLN);
        if (isEnoughMoney(plnBalance, amountToExchange)) {
            BigDecimal exchangeRatePlnToUsd = exchangeRateService.getPlnToUsdAskExchangeRate();
            BigDecimal usdBalance = account.getBalance().get(CurrencyType.USD);
            BigDecimal plnBalanceAfterExchange = plnBalance.subtract(amountToExchange);
            BigDecimal usdBalanceAfterExchange = usdBalance.add(amountToExchange.divide((exchangeRatePlnToUsd), 4, RoundingMode.HALF_UP));

            updateRepository(account.getAccountId(), plnBalanceAfterExchange, usdBalanceAfterExchange);

            account.setBalance(CurrencyType.PLN, plnBalanceAfterExchange);
            account.setBalance(CurrencyType.USD, usdBalanceAfterExchange);

            return accountMapper.mapAccountToExchangeResponse(account);
        } else {
            throw new InsufficientFundsException("Not enough PLN balance to exchange");
        }
    }

    private ExchangeResponse exchangeFromUsdToPln(Account account, BigDecimal amountToExchange) {
        BigDecimal usdBalance = account.getBalance().get(CurrencyType.USD);
        if (isEnoughMoney(usdBalance, amountToExchange)) {
            BigDecimal exchangeRateUsdToPln = exchangeRateService.getPlnToUsdBidExchangeRate();
            BigDecimal plnBalance = account.getBalance().get(CurrencyType.PLN);
            BigDecimal usdAmountAfterExchange = usdBalance.subtract(amountToExchange);
            BigDecimal plnAmountAfterExchange = plnBalance.add(amountToExchange.multiply(exchangeRateUsdToPln));

            updateRepository(account.getAccountId(), plnAmountAfterExchange, usdAmountAfterExchange);

            account.setBalance(CurrencyType.USD, usdAmountAfterExchange);
            account.setBalance(CurrencyType.PLN, plnAmountAfterExchange);

            return accountMapper.mapAccountToExchangeResponse(account);
        } else {
            throw new InsufficientFundsException("Not enough USD balance to exchange");
        }
    }

    private static boolean isEnoughMoney(BigDecimal accountBalance, BigDecimal amountToExchange) {
        return accountBalance.compareTo(amountToExchange) >= 0;
    }

    private void updateRepository(UUID accountId, BigDecimal plnBalance, BigDecimal usdBalance) {
        accountRepository.updateBalance(accountId, CurrencyType.PLN, plnBalance);
        accountRepository.updateBalance(accountId, CurrencyType.USD, usdBalance);
    }
}
