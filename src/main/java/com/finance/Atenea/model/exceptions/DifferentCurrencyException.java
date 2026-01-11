package com.finance.Atenea.model.exceptions;

public class DifferentCurrencyException extends RuntimeException {
    public DifferentCurrencyException(String message) {
        super(message);
    }
}
