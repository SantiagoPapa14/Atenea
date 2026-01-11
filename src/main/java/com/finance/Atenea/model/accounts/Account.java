package com.finance.Atenea.model.accounts;

import com.finance.Atenea.model.MarketService;
import com.finance.Atenea.model.assets.Money;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    protected Client client;

    @Column
    protected String name;

    public Account(Client client, String name) {
        this.client = client;
        this.name = name;
    }

    public abstract Money getNetWorth(MarketService marketData);

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
