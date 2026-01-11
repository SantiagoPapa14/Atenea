package com.finance.Atenea.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.finance.Atenea.model.Currency;
import com.finance.Atenea.model.Transaction;
import com.finance.Atenea.model.accounts.SavingsAccount;
import com.finance.Atenea.model.accounts.Account;
import com.finance.Atenea.model.accounts.Client;
import com.finance.Atenea.model.accounts.InvestmentAccount;
import com.finance.Atenea.repository.AccountRepository;
import com.finance.Atenea.repository.TransactionRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
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

    public Iterable<Account> getAccounts(Client client) {
        return accountRepository.findByClient(client);
    }

    public Account getAccount(Client client, Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            throw new IllegalArgumentException("Account not found");
        }
        if (account.get().getClient() != client) {
            throw new IllegalArgumentException("Client does not own account");
        }
        return account.get();
    }

    public Iterable<Transaction> getAccountTransactions(Client client, Long id) {
        Account account = getAccount(client, id);
        if (!(account instanceof SavingsAccount)) {
            throw new IllegalArgumentException("Account is not a savings account");
        }
        return transactionRepository.findByAccount((SavingsAccount) account);
    }

    public Transaction deposit(Client client, Long accountId, Transaction transaction) {
        SavingsAccount savingsAccount = (SavingsAccount) getAccount(client, accountId);
        transaction.setAccount(savingsAccount);
        Transaction processedTransaction = savingsAccount.process(transactionRepository.save(transaction));
        accountRepository.save(savingsAccount);
        return processedTransaction;
    }

    public Transaction spend(Client client, Long accountId, Transaction transaction) {
        transaction.getAmount().invert();
        return this.deposit(client, accountId, transaction);
    }
}
