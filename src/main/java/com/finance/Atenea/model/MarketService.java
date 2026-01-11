package com.finance.Atenea.model;

import com.finance.Atenea.model.assets.Money;

public interface MarketService {
    Money stockPrice(String ticker);

    Money fxRate(Currency from, Currency to);
}
