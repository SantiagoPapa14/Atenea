package com.finance.Atenea.repository;

import com.finance.Atenea.model.accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
