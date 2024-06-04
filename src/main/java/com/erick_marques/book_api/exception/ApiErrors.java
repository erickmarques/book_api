package com.erick_marques.book_api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * Classe para encapsular os erros da API.
 */
@Data
@AllArgsConstructor
public class ApiErrors {
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
