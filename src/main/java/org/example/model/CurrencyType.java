package org.example.model;

import lombok.Getter;


public enum CurrencyType {
    PLN("Polish Zloty"),
    USD("United States Dollar");

    @Getter
    private final String description;

    CurrencyType(String description) {
        this.description = description;
    }

    public static CurrencyType fromString(String currency) {
        for (CurrencyType c : CurrencyType.values()) {
            if (c.name().equalsIgnoreCase(currency)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Invalid currency type: " + currency);
    }
}
