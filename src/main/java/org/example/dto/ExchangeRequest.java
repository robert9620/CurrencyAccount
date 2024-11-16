package org.example.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.model.CurrencyType;

import java.math.BigDecimal;

@Getter
@Setter
public class ExchangeRequest {
    @NotNull(message = "Amount to exchange is mandatory")
    @DecimalMin(value = "0.01", inclusive = true, message = "Amount to exchange must be at least 0.01")
    private BigDecimal amountToExchange;

    @NotNull(message = "Currency from exchange to is mandatory")
    private CurrencyType currencyFrom;

    @NotNull(message = "Currency to exchange to is mandatory")
    private CurrencyType currencyTo;
}
