package com.erick_marques.book_api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.erick_marques.book_api.util.BookUtil;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Classe de teste para {@link MessageService}.
 */
@SpringBootTest
@DisplayName("Testes para o serviço de Mensagem")
class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    /**
     * Testa se a mensagem para 'user.loginInvalid' é retornada corretamente.
     */
    @Test
    @DisplayName("Teste recuperação de mensagem - Login Inválido")
    void testGetMessage_LoginInvalid() {
        String message = messageService.getMessage("user.loginInvalid");
        assertThat(message).isEqualTo("Login/senha inválidos!");
    }

    /**
     * Testa se a mensagem para 'user.login.empty' é retornada corretamente.
     */
    @Test
    @DisplayName("Teste recuperação de mensagem - Login Obrigatório")
    void testGetMessage_LoginRequired() {
        String message = messageService.getMessage("user.login.empty");
        assertThat(message).isEqualTo("Login é obrigatório!");
    }


    /**
     * Testa se a mensagem para 'book.notFound' é retornada corretamente.
     */
    @Test
    @DisplayName("Teste recuperação de mensagem - Login Obrigatório")
    void testGetMessage_BookNotFound() {

        String idString = String.valueOf(BookUtil.ID_NEGATIVE);
        String message = messageService.getMessage("book.notFound", idString);
        assertThat(message).isEqualTo("Livro não encontrado com ID: " + idString);
    }
}