package com.erick_marques.book_api.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.erick_marques.book_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService  {

    private final UserRepository repository;
    private final MessageService messageService;

    @Override
    public UserDetails loadUserByUsername(String username){
        
        return repository.findByLogin(username)
                            .orElseThrow(() -> new UsernameNotFoundException(messageService.getMessage("user.loginInvalid")));
    }
}
