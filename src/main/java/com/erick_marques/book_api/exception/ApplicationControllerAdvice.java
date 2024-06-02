package com.erick_marques.book_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe para tratamento global de exceções na aplicação.
 */
@RestControllerAdvice
public class ApplicationControllerAdvice {

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
    public ResponseEntity handleResponseStatusException(ResponseStatusException ex) {
        String errorMessage = ex.getReason();
        HttpStatusCode statusCode = ex.getStatusCode();
        ApiErrors apiErrors = new ApiErrors(errorMessage);
        return new ResponseEntity(apiErrors, statusCode);
    }
}
