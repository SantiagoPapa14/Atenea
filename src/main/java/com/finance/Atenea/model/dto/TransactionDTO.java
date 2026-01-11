package com.finance.Atenea.model.dto;

import java.sql.Date;

import com.finance.Atenea.model.Transaction;
import com.finance.Atenea.model.TransactionCategory;
import com.finance.Atenea.model.assets.Money;

public record TransactionDTO(
        Long id,
        Long accountId,
        String description,
        Money amount,
        TransactionCategory category,
        Date date) {

    public TransactionDTO(Transaction transaction) {
        this(transaction.getId(), transaction.getAccount().getId(), transaction.getDescription(),
                transaction.getAmount(), transaction.getCategory(), transaction.getDate());
    }
}
