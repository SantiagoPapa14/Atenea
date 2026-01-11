package com.finance.Atenea.controller;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.Atenea.model.dto.LoginRequest;
import com.finance.Atenea.model.dto.RegisterRequest;
import com.finance.Atenea.model.dto.TokenResponse;
import com.finance.Atenea.service.ClientService;
import com.finance.Atenea.service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final ClientService clientService;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authManager, JwtService jwtService, ClientService clientService) {
        this.authManager = authManager;
        this.clientService = clientService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest request) {
        clientService.create(request.username(), request.password());
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest request) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(), request.password()));

        String token = jwtService.generate(request.username());
        return new TokenResponse(token);
    }

    @GetMapping("/me")
    public String me(Authentication auth) {
        return auth.getUsername();
    }
}
