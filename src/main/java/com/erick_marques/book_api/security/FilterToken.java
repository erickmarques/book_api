package com.erick_marques.book_api.security;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.erick_marques.book_api.entity.User;
import com.erick_marques.book_api.repository.UserRepository;
import com.erick_marques.book_api.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FilterToken extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            
        String token;

        var authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader != null && !authorizationHeader.isEmpty()) {
            token = authorizationHeader.replace("Bearer ", "");
            
            String subject = this.tokenService.getSubject(token);

            User user = this.repository.findByLogin(subject)
                                .orElseThrow(() -> new UsernameNotFoundException("Login/Senha inválidos!"));

            var authentication = new UsernamePasswordAuthenticationToken(user,
                    null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
