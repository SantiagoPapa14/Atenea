package com.finance.Atenea.service;

import org.springframework.stereotype.Service;

import com.finance.Atenea.model.Currency;
import com.finance.Atenea.model.accounts.SavingsAccount;
import com.finance.Atenea.model.accounts.Client;
import com.finance.Atenea.model.accounts.InvestmentAccount;
import com.finance.Atenea.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public SavingsAccount createSavingsAccount(Client client, String name, Currency currency) {
        SavingsAccount savingsAccount = new SavingsAccount(client, name, currency);
        accountRepository.save(savingsAccount);
        return savingsAccount;
    }

    public InvestmentAccount createInvestmentAccount(Client client, String name) {
        InvestmentAccount investmentAccount = new InvestmentAccount(client, name);
        accountRepository.save(investmentAccount);
        return investmentAccount;
    }

}
