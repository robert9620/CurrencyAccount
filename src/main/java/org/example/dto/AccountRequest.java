package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountRequest {
    @NotBlank(message = "Name is mandatory")
    private String firstName;

    @NotBlank(message = "Surname is mandatory")
    private String lastName;

    @NotNull(message = "Initial balance is mandatory")
    private BigDecimal initialPLNBalance;

    @NotNull(message = "Initial balance is mandatory")
    private BigDecimal initialUSDBalance;
}
