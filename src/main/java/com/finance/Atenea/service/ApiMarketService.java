package com.finance.Atenea.service;

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

    private long lastRequestTime = 0;
    private static final long MIN_REQUEST_INTERVAL_MS = 1100;

    public ApiMarketService(
            RestTemplate restTemplate,
            @Value("${alpha.vantage.api.key}") String apiKey) {

        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    private synchronized void throttle() {
        long now = System.currentTimeMillis();
        long elapsed = now - lastRequestTime;

        if (elapsed < MIN_REQUEST_INTERVAL_MS) {
            long sleepTime = MIN_REQUEST_INTERVAL_MS - elapsed;
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread interrupted while throttling", e);
            }
        }

        lastRequestTime = System.currentTimeMillis();
    }

    @Override
    public Money stockPrice(String ticker) {
        throttle();
        String url = new AlphaVantageUrlBuilder().function(AlphaVantageApiFunction.GLOBAL_QUOTE).symbol(ticker)
                .apiKey(apiKey)
                .build();

        AlphaVantageGlobalQuoteResponse response = restTemplate.getForObject(url,
                AlphaVantageGlobalQuoteResponse.class);

        AlphaVantageGlobalQuoteDTO quoteData = response.quote();

        if (quoteData == null || quoteData.price() == null) {
            throw new IllegalStateException("Invalid response from AlphaVantage for symbol " + ticker);
        }

        return Money.of(Currency.USD, quoteData.price());
    }

    @Override
    public Money fxRate(Currency from, Currency to) {
        throttle();
        String url = new AlphaVantageUrlBuilder().function(AlphaVantageApiFunction.CURRENCY_EXCHANGE_RATE).from(from)
                .to(to)
                .apiKey(apiKey)
                .build();

        AlphaVantageExchangeRateResponse response = restTemplate.getForObject(url,
                AlphaVantageExchangeRateResponse.class);

        if (response == null || response.exchangeRateData() == null) {
            throw new IllegalStateException(
                    "Invalid response from AlphaVantage for " + from + " to " + to);
        }

        AlphaVantageExchangeRateDTO exchangeRateData = response.exchangeRateData();

        return Money.of(to, exchangeRateData.exchangeRate());

    }
}
