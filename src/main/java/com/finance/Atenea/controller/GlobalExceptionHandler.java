package com.finance.Atenea.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.finance.Atenea.model.exceptions.AccountNotFoundException;
import com.finance.Atenea.model.exceptions.AccountTypeMismatchException;
import com.finance.Atenea.model.exceptions.DifferentCurrencyException;
import com.finance.Atenea.model.exceptions.OwnershipException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OwnershipException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> handleOwnershipException(OwnershipException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(DifferentCurrencyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleDifferentCurrency(DifferentCurrencyException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(AccountTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleAccountTypeMismatchException(AccountTypeMismatchException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleAccountNotFoundException(AccountNotFoundException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleException(Exception ex) {
        return Map.of("error", ex.getMessage());
    }
}
