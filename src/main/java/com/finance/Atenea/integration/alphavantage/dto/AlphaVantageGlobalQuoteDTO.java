package com.finance.Atenea.integration.alphavantage.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AlphaVantageGlobalQuoteDTO(
        @JsonProperty("01. symbol") String symbol,
        @JsonProperty("02. open") BigDecimal open,
        @JsonProperty("03. high") BigDecimal high,
        @JsonProperty("04. low") BigDecimal low,
        @JsonProperty("05. price") BigDecimal price,
        @JsonProperty("06. volume") Long volume,
        @JsonProperty("07. latest trading day") String latestTradingDay,
        @JsonProperty("08. previous close") BigDecimal previousClose,
        @JsonProperty("09. change") BigDecimal change,
        @JsonProperty("10. change percent") String changePercent) {
}

/*
 * {
 * "Global Quote": {
 * "01. symbol": "IBM",
 * "02. open": "302.6100",
 * "03. high": "307.0000",
 * "04. low": "302.0000",
 * "05. price": "304.2200",
 * "06. volume": "2718828",
 * "07. latest trading day": "2026-01-09",
 * "08. previous close": "302.7200",
 * "09. change": "1.5000",
 * "10. change percent": "0.4955%"
 * }
 * }
 */
