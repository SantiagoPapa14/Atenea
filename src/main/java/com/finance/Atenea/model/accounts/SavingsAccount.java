package com.finance.Atenea.model.accounts;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finance.Atenea.model.Currency;
import com.finance.Atenea.model.MarketService;
import com.finance.Atenea.model.Transaction;
import com.finance.Atenea.model.assets.Money;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class SavingsAccount extends Account {
    @Embedded
    private Money balance;

    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new java.util.ArrayList<>();

    public SavingsAccount(Client client, String name, Currency currency) {
        super(client, name);
        this.balance = Money.zero(currency);
    }

    public SavingsAccount() {
        super();
    }

    public Transaction process(Transaction transaction) {
        if (transaction.getAmount().getCurrency() != balance.getCurrency()) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        balance = balance.add(transaction.getAmount());
        transactions.add(transaction);
        return transaction;
    }

    @Override
    public Money getNetWorth(MarketService marketData) {
        return balance;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
