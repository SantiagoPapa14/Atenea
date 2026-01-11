package com.finance.Atenea.model;

import java.sql.Date;

import org.springframework.boot.context.properties.bind.DefaultValue;

import com.finance.Atenea.model.accounts.SavingsAccount;
import com.finance.Atenea.model.assets.Money;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    protected SavingsAccount account;

    @Column
    private String description;

    @Column
    @Embedded
    private Money amount;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionCategory category;

    @Column
    private Date date = new Date(System.currentTimeMillis());

    public Transaction(String description, Money amount, TransactionCategory category, SavingsAccount account) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.account = account;
    }

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public void setType(TransactionCategory category) {
        this.category = category;
    }

    public SavingsAccount getAccount() {
        return account;
    }

    public void setAccount(SavingsAccount account) {
        this.account = account;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
