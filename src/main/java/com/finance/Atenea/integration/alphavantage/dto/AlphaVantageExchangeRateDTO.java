package com.finance.Atenea.integration.alphavantage.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AlphaVantageExchangeRateDTO(
        @JsonProperty("1. From_Currency Code") String fromCurrencyCode,
        @JsonProperty("2. From_Currency Name") String fromCurrencyName,
        @JsonProperty("3. To_Currency Code") String toCurrencyCode,
        @JsonProperty("4. To_Currency Name") String toCurrencyName,
        @JsonProperty("5. Exchange Rate") BigDecimal exchangeRate,
        @JsonProperty("6. Last Refreshed") String lastRefreshed,
        @JsonProperty("7. Time Zone") String timeZone,
        @JsonProperty("8. Bid Price") BigDecimal bidPrice,
        @JsonProperty("9. Ask Price") BigDecimal askPrice) {
}

/*
 * {
 * "Realtime Currency Exchange Rate": {
 * "1. From_Currency Code": "EUR",
 * "2. From_Currency Name": "Euro",
 * "3. To_Currency Code": "USD",
 * "4. To_Currency Name": "United States Dollar",
 * "5. Exchange Rate": "1.16390000",
 * "6. Last Refreshed": "2026-01-11 01:37:38",
 * "7. Time Zone": "UTC",
 * "8. Bid Price": "1.16388000",
 * "9. Ask Price": "1.16395000"
 * }
 * }
 */
