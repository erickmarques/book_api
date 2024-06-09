package com.erick_marques.book_api.service;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MessageService {
    
    private final MessageSource messageSource;

    /**
     * Recupera uma mensagem localizada para o código e argumentos fornecidos.
     *
     * @param code o código da mensagem.
     * @param args os argumentos para a mensagem.
     * @return a mensagem localizada.
     */
    public String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, Locale.getDefault());
    }
}
