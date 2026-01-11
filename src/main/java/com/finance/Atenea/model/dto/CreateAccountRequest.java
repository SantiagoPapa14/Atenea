package com.finance.Atenea.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finance.Atenea.model.Currency;

public record CreateAccountRequest(
        String name,
        @JsonProperty(required = false) Currency currency) {
}
