package com.finance.Atenea.model.assets;

import com.finance.Atenea.model.MarketService;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Stock extends Asset {
    @Column
    private String ticker;

    @Column
    private Integer amount;

    public Money getWorth(MarketService marketData) {
        return marketData.stockPrice(this.ticker).multiply(this.amount);
    }
}
