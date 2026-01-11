package com.finance.Atenea.model.exceptions;

public class AccountTypeMismatchException extends RuntimeException {
    public AccountTypeMismatchException(String message) {
        super(message);
    }

    public AccountTypeMismatchException() {
        super("Account type is not valid for this operation");
    }
}
