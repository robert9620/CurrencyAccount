package org.example.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String accountId) {
        super("Account with ID " + accountId + " not found.");
    }

    public AccountNotFoundException(String accountId, Throwable cause) {
        super("Account with ID " + accountId + " not found.", cause);
    }
}
