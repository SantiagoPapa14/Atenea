package com.finance.Atenea.integration.alphavantage;

import com.finance.Atenea.model.Currency;

public class AlphaVantageUrlBuilder {

    private static final String ROOT_URL = "https://www.alphavantage.co/query";

    private AlphaVantageApiFunction function;
    private String symbol;
    private Currency from;
    private Currency to;
    private String apiKey;

    public AlphaVantageUrlBuilder function(AlphaVantageApiFunction function) {
        this.function = function;
        return this;
    }

    public AlphaVantageUrlBuilder symbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public AlphaVantageUrlBuilder from(Currency from) {
        this.from = from;
        return this;
    }

    public AlphaVantageUrlBuilder to(Currency to) {
        this.to = to;
        return this;
    }

    public AlphaVantageUrlBuilder apiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public String build() {
        StringBuilder url = new StringBuilder(ROOT_URL);
        url.append("?function=").append(function.name());

        if (symbol != null) {
            url.append("&symbol=").append(symbol);
        }

        if (from != null && to != null) {
            url.append("&from_currency=").append(from.name());
            url.append("&to_currency=").append(to.name());
        }

        url.append("&apikey=").append(apiKey);
        return url.toString();
    }
}
