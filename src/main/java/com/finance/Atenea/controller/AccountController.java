package com.finance.Atenea.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.finance.Atenea.model.dto.CreateAccountRequest;
import com.finance.Atenea.model.exceptions.AccountTypeMismatchException;
import com.finance.Atenea.service.AccountService;
import com.finance.Atenea.service.ClientService;
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

    @DeleteMapping("{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long accountId) {
        Client client = clientService.findByUsername(userDetails.getUsername());
        accountService.deleteAccount(client, accountId);
    }
}
