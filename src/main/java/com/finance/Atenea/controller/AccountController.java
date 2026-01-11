package com.finance.Atenea.controller;

import java.net.Authenticator;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finance.Atenea.model.dto.CreateAccountRequest;
import com.finance.Atenea.service.AccountService;
import com.finance.Atenea.service.ClientService;
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
            throw new IllegalArgumentException("Invalid account type: " + accountType);
        }
    }

}
