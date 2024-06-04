package com.erick_marques.book_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;


import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

/**
 * Classe para tratamento global de exceções na aplicação.
 */
@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrors handleUsernameNotFoundException(UsernameNotFoundException ex, HttpServletRequest request) {
        String errorMessage = ex.getMessage();
        return new ApiErrors(LocalDateTime.now().toString(), HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.toString(), errorMessage, request.getRequestURI());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrors handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
        String errorMessage = "Login/senha inválidos!";
        return new ApiErrors(LocalDateTime.now().toString(), HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.toString(), errorMessage, request.getRequestURI());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrors> handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request) {
        String errorMessage = ex.getReason();
        HttpStatusCode statusCode = ex.getStatusCode();

        ApiErrors apiErrors = new ApiErrors(LocalDateTime.now().toString(), statusCode.value(), statusCode.toString(), errorMessage, request.getRequestURI());
        return new ResponseEntity<>(apiErrors, statusCode);
    }
}
