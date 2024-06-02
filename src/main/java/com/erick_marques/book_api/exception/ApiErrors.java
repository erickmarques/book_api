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
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrors {

    /**
     * Lista de mensagens de erro.
     */
    private List<String> errors;

    /**
     * Construtor que inicializa a lista de erros com uma única mensagem.
     *
     * @param msg a mensagem de erro a ser adicionada à lista.
     */
    public ApiErrors(String msg){
        this.errors = Arrays.asList(msg);
    }
}
