package com.finance.Atenea.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.finance.Atenea.integration.alphavantage.AlphaVantageApiFunction;
import com.finance.Atenea.integration.alphavantage.AlphaVantageUrlBuilder;
import com.finance.Atenea.integration.alphavantage.dto.AlphaVantageExchangeRateDTO;
import com.finance.Atenea.integration.alphavantage.dto.AlphaVantageExchangeRateResponse;
import com.finance.Atenea.integration.alphavantage.dto.AlphaVantageGlobalQuoteDTO;
import com.finance.Atenea.integration.alphavantage.dto.AlphaVantageGlobalQuoteResponse;
import com.finance.Atenea.model.Currency;
import com.finance.Atenea.model.MarketService;
import com.finance.Atenea.model.assets.Money;

@Service
public class ApiMarketService implements MarketService {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private static final Logger log = LoggerFactory.getLogger(ApiMarketService.class);

    public ApiMarketService(
            RestTemplate restTemplate,
            @Value("${alpha.vantage.api.key}") String apiKey) {

        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    @Override
    public Money stockPrice(String ticker) {
        String url = new AlphaVantageUrlBuilder().function(AlphaVantageApiFunction.GLOBAL_QUOTE).symbol(ticker)
                .apiKey(apiKey)
                .build();

        AlphaVantageGlobalQuoteResponse response = restTemplate.getForObject(url,
                AlphaVantageGlobalQuoteResponse.class);
        log.info("AlphaVantage GLOBAL_QUOTE raw response: {}", response);

        AlphaVantageGlobalQuoteDTO quoteData = response.quote();
        log.info("AlphaVantage GLOBAL_QUOTE data: {}", quoteData);

        if (quoteData == null || quoteData.price() == null) {
            log.error("Invalid GLOBAL_QUOTE response for {}: {}", ticker, quoteData);
            throw new IllegalStateException("Invalid response from AlphaVantage for symbol " + ticker);
        }

        return Money.of(Currency.USD, quoteData.price());
    }

    @Override
    public Money fxRate(Currency from, Currency to) {
        String url = new AlphaVantageUrlBuilder().function(AlphaVantageApiFunction.CURRENCY_EXCHANGE_RATE).from(from)
                .to(to)
                .apiKey(apiKey)
                .build();

        System.out.println("\n" + url);

        AlphaVantageExchangeRateResponse response = restTemplate.getForObject(url,
                AlphaVantageExchangeRateResponse.class);

        log.info("AlphaVantage FX_RATE raw response: {}", response);

        if (response == null || response.exchangeRateData() == null) {
            throw new IllegalStateException(
                    "Invalid response from AlphaVantage for " + from + " to " + to);
        }

        AlphaVantageExchangeRateDTO exchangeRateData = response.exchangeRateData();
        log.info("AlphaVantage FX_RATE data: {}", exchangeRateData);

        return Money.of(to, exchangeRateData.exchangeRate());

    }
}
