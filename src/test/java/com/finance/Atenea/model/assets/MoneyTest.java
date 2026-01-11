package com.finance.Atenea.model.assets;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.finance.Atenea.model.Currency;

class MoneyTest {

    @Test
    void zeroCreatesZeroAmount() {
        Money zero = Money.zero(Currency.USD);

        assertEquals(BigDecimal.ZERO, zero.getAmount());
        assertEquals(Currency.USD, zero.getCurrency());
    }

    @Test
    void addSameCurrencyWorks() {
        Money m1 = Money.of(Currency.USD, new BigDecimal("10.00"));
        Money m2 = Money.of(Currency.USD, new BigDecimal("5.50"));

        Money result = m1.add(m2);

        assertEquals(new BigDecimal("15.50"), result.getAmount());
        assertEquals(Currency.USD, result.getCurrency());
    }

    @Test
    void addDifferentCurrencyThrows() {
        Money usd = Money.of(Currency.USD, new BigDecimal("10.00"));
        Money eur = Money.of(Currency.EUR, new BigDecimal("5.00"));

        assertThrows(IllegalArgumentException.class, () -> usd.add(eur));
    }

    @Test
    void multiplyWorks() {
        Money m1 = Money.of(Currency.USD, new BigDecimal("10.00"));

        Money result = m1.multiply(2);

        assertEquals(new BigDecimal("20.00"), result.getAmount());
        assertEquals(Currency.USD, result.getCurrency());
    }
}
