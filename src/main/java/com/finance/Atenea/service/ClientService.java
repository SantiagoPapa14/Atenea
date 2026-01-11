package com.finance.Atenea.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.finance.Atenea.model.accounts.Client;
import com.finance.Atenea.repository.ClientRepository;

@Service
public class ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder encoder;

    public ClientService(ClientRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public Client create(String username, String rawPassword) {
        Client c = new Client();
        c.setUsername(username);
        c.setPassword(encoder.encode(rawPassword));
        return repository.save(c);
    }

    public Client findByUsername(String username) {
        Optional<Client> client = repository.findByUsername(username);
        if (client.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return client.get();
    }
}
