package com.finance.Atenea.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.finance.Atenea.model.accounts.Client;
import com.finance.Atenea.repository.ClientRepository;

@Service
public class ClientDetailsService implements UserDetailsService {

    private final ClientRepository repository;

    public ClientDetailsService(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Optional<Client> client = repository.findByUsername(username);
        if (client.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return User.builder()
                .username(client.get().getUsername())
                .password(client.get().getPassword())
                .roles("USER")
                .build();
    }
}
