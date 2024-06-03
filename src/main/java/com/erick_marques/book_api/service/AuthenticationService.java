package com.erick_marques.book_api.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.erick_marques.book_api.entity.User;
import com.erick_marques.book_api.exception.UserIvalidException;
import com.erick_marques.book_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService  {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username){
        
        User user = repository.findByLogin(username)
                            .orElseThrow(() -> new UserIvalidException());

        return user;
    }
}
