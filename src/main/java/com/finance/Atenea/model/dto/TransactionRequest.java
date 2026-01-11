package com.finance.Atenea.model.dto;

import com.finance.Atenea.model.TransactionCategory;
import com.finance.Atenea.model.assets.Money;

public record TransactionRequest(Money amount, TransactionCategory category) {
}
