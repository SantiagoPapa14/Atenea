package com.finance.Atenea.repository;

import com.finance.Atenea.model.accounts.Account;
import com.finance.Atenea.model.accounts.Client;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    public Iterable<Account> findByClient(Client client);
}
