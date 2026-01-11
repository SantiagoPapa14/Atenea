package com.finance.Atenea.model.assets;

import java.math.BigDecimal;

import com.finance.Atenea.model.Currency;
import com.finance.Atenea.model.MarketService;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class Money {

    @Column
    private BigDecimal amount;

    @Column
    @Enumerated(EnumType.STRING)
    private Currency currency;

    private Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money() {
    }

    public static Money zero(Currency currency) {
        return new Money(BigDecimal.ZERO, currency);
    }

    public static Money of(Currency currency, BigDecimal amount) {
        return new Money(amount, currency);
    }

    public Money getWorth(MarketService marketData, Currency targetCurrency) {
        if (this.currency != targetCurrency) {
            BigDecimal amountUSD = this.amount.multiply(marketData.fxRate(this.currency, targetCurrency).getAmount());
            return new Money(amountUSD, targetCurrency);
        }
        return this;
    }

    public Money getWorth(MarketService marketData) {
        return this.getWorth(marketData, Currency.USD);
    }

    public Money add(Money money) {
        if (this.currency != money.currency) {
            throw new IllegalArgumentException("Cannot add different currencies");
        }
        return new Money(this.amount.add(money.getAmount()), this.currency);
    }

    public Money multiply(Integer factor) {
        return new Money(this.amount.multiply(new BigDecimal(factor)), this.currency);
    }

    public void invert() {
        this.amount = this.amount.negate();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }
}
