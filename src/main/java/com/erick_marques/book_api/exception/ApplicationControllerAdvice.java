package com.erick_marques.book_api.exception;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Classe para tratamento global de exceções na aplicação.
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class ApplicationControllerAdvice {

    private final MessageSource messageSource;

    /**
     * Método para lidar com exceções de validação.
     *
     * @param ex a exceção do tipo MethodArgumentNotValidException.
     * @return um objeto ApiErrors contendo os erros de validação.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationErrors(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errors = bindingResult.getAllErrors()
                .stream()
                .map(obj -> obj.getDefaultMessage())
                .collect(Collectors.toList());
        return new ApiErrors(errors);
    }

    /**
     * Método para lidar com exceções de status de resposta.
     *
     * @param ex a exceção do tipo ResponseStatusException.
     * @return uma resposta com o status de erro e a mensagem fornecida pela exceção.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrors> handleResponseStatusException(ResponseStatusException ex) {
        String errorMessage = ex.getReason();
        HttpStatusCode statusCode = ex.getStatusCode();
        ApiErrors apiErrors = new ApiErrors(errorMessage);
        return new ResponseEntity<>(apiErrors, statusCode);
    }


    @ExceptionHandler({UserIvalidException.class})
    public ResponseEntity<UserInvalid> handleUserIvalidException(RuntimeException ex) {
        UserInvalid restError = new UserInvalid(getMessage("user.loginInvalid"));
        return new ResponseEntity<>(restError, HttpStatus.FORBIDDEN);
    }


    /**
     * Recupera uma mensagem localizada para o código e argumentos fornecidos.
     *
     * @param code o código da mensagem.
     * @param args os argumentos para a mensagem.
     * @return a mensagem localizada.
     */
    private String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.getDefault());
    }

}
