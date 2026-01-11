package com.finance.Atenea.integration.alphavantage.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AlphaVantageExchangeRateResponse(
        @JsonProperty("Realtime Currency Exchange Rate") AlphaVantageExchangeRateDTO exchangeRateData) {
}
