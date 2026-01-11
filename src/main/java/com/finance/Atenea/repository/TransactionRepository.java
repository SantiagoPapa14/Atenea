package com.finance.Atenea.repository;

import com.finance.Atenea.model.Transaction;
import com.finance.Atenea.model.accounts.SavingsAccount;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Iterable<Transaction> findByAccount(SavingsAccount account);
}
