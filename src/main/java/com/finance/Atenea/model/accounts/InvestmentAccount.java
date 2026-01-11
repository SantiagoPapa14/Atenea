package com.finance.Atenea.model.accounts;

import java.util.List;

import com.finance.Atenea.model.Currency;
import com.finance.Atenea.model.MarketService;
import com.finance.Atenea.model.assets.Asset;
import com.finance.Atenea.model.assets.Money;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class InvestmentAccount extends Account {

    @OneToMany(mappedBy = "account")
    private List<Asset> assets = new java.util.ArrayList<>();

    public InvestmentAccount(Client client, String name) {
        super(client, name);
    }

    @Override
    public Money getNetWorth(MarketService marketData) {
        return assets.stream()
                .map(a -> a.getWorth(marketData))
                .reduce(Money.zero(Currency.USD), Money::add);
    }
}
