package com.finance.Atenea.model.accounts;

import com.finance.Atenea.model.Currency;
import com.finance.Atenea.model.MarketService;
import com.finance.Atenea.model.assets.Money;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;

@Entity
public class SavingsAccount extends Account {
    @Embedded
    private Money balance;

    public SavingsAccount(Client client, String name, Currency currency) {
        super(client, name);
        this.balance = Money.zero(currency);
    }

    @Override
    public Money getNetWorth(MarketService marketData) {
        return balance;
    }
}
