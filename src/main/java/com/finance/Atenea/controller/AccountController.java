package com.finance.Atenea.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finance.Atenea.model.dto.CreateAccountRequest;
import com.finance.Atenea.model.dto.TransactionDTO;
import com.finance.Atenea.model.exceptions.AccountTypeMismatchException;
import com.finance.Atenea.model.exceptions.DifferentCurrencyException;
import com.finance.Atenea.service.AccountService;
import com.finance.Atenea.service.ClientService;
import com.finance.Atenea.model.Transaction;
import com.finance.Atenea.model.accounts.Account;
import com.finance.Atenea.model.accounts.Client;

@RestController
@RequestMapping("/account")
public class AccountController {
    private AccountService accountService;
    private ClientService clientService;

    public AccountController(AccountService accountService, ClientService clientService) {
        this.accountService = accountService;
        this.clientService = clientService;
    }

    @PostMapping("/create")
    public void create(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam String accountType,
            @RequestBody CreateAccountRequest req) {

        Client client = clientService.findByUsername(userDetails.getUsername());

        if ("INVESTMENT".equalsIgnoreCase(accountType)) {
            accountService.createInvestmentAccount(client, req.name());
        } else if ("SAVINGS".equalsIgnoreCase(accountType)) {
            accountService.createSavingsAccount(client, req.name(), req.currency());
        } else {
            throw new AccountTypeMismatchException("Invalid account type");
        }
    }

    @GetMapping("/all")
    public Iterable<Account> getAccounts(@AuthenticationPrincipal UserDetails userDetails) {
        Client client = clientService.findByUsername(userDetails.getUsername());
        return accountService.getAccounts(client);
    }

    @GetMapping("{accountId}")
    public Account getAccount(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long accountId) {
        Client client = clientService.findByUsername(userDetails.getUsername());
        return accountService.getAccount(client, accountId);
    }

    @PostMapping("/{accountId}/deposit")
    public void deposit(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long accountId,
            @RequestBody Transaction transaction) {
        Client client = clientService.findByUsername(userDetails.getUsername());
        accountService.deposit(client, accountId, transaction);
    }

    @PostMapping("/{accountId}/spend")
    public void spend(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long accountId,
            @RequestBody Transaction transaction) {
        Client client = clientService.findByUsername(userDetails.getUsername());
        accountService.spend(client, accountId, transaction);
    }

    @GetMapping("{accountId}/transactions")
    public Iterable<TransactionDTO> getAccountTransactions(@AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long accountId) {
        Client client = clientService.findByUsername(userDetails.getUsername());
        Iterable<Transaction> transactions = accountService.getAccountTransactions(client, accountId);
        List<TransactionDTO> transactionDTOs = new java.util.ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionDTOs.add(new TransactionDTO(transaction));
        }
        return transactionDTOs;
    }

}
