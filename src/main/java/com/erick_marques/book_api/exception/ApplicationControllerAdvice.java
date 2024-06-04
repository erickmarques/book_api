package com.erick_marques.book_api.exception;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.auth0.jwt.exceptions.TokenExpiredException;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errors = bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        return new ApiErrors(LocalDateTime.now().toString(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), String.join(", ", errors), request.getRequestURI());
    }

    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrors handleTokenExpiredException(TokenExpiredException ex, HttpServletRequest request) {
        String errorMessage = "Token expirado!";
        return new ApiErrors(LocalDateTime.now().toString(), HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.toString(), errorMessage, request.getRequestURI());
    }

}
