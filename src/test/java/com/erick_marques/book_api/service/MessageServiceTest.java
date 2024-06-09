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
     * Testa se a mensagem para 'user.expiredToken' é retornada corretamente.
     */
    @Test
    @DisplayName("Teste recuperação de mensagem - Token Expirado")
    void testGetMessage_UserExpiredToken() {
        String message = messageService.getMessage("user.expiredToken");
        assertThat(message).isEqualTo("Token expirado!");
    }

    /**
     * Testa se a mensagem para 'user.login.size' é retornada corretamente.
     */
    @Test
    @DisplayName("Teste recuperação de mensagem - Tamanho do Login Excedido")
    void testGetMessage_UserLoginSizeExceeded() {
        String message = messageService.getMessage("user.login.size");
        assertThat(message).isEqualTo("Login não pode ter mais de 100 caracteres.");
    }

    /**
     * Testa se a mensagem para 'user.password.empty' é retornada corretamente.
     */
    @Test
    @DisplayName("Teste recuperação de mensagem - Senha do Usuário Obrigatória")
    void testGetMessage_UserPasswordEmpty() {
        String message = messageService.getMessage("user.password.empty");
        assertThat(message).isEqualTo("Senha é obrigatório!");
    }

    /**
     * Testa se a mensagem para 'user.password.size' é retornada corretamente.
     */
    @Test
    @DisplayName("Teste recuperação de mensagem - Tamanho da Senha do Usuário Excedido")
    void testGetMessage_UserPasswordSizeExceeded() {
        String message = messageService.getMessage("user.password.size");
        assertThat(message).isEqualTo("Senha não pode ter mais de 100 caracteres.");
    }

    /**
     * Testa se a mensagem para 'book.notFound' é retornada corretamente quando o ID é negativo.
     */
    @Test
    @DisplayName("Teste recuperação de mensagem - Livro Não Encontrado")
    void testGetMessage_BookNotFound() {

        String idString = String.valueOf(BookUtil.ID_NEGATIVE);
        String message = messageService.getMessage("book.notFound", idString);
        assertThat(message).isEqualTo("Livro não encontrado com ID: " + idString );
    }

    /**
     * Testa se a mensagem para 'book.id.invalid' é retornada corretamente.
     */
    @Test
    @DisplayName("Teste recuperação de mensagem - ID do Livro Inválido")
    void testGetMessage_BookIdInvalid() {
        String message = messageService.getMessage("book.id.invalid");
        assertThat(message).isEqualTo("Livro com ID inválido!");
    }

    /**
     * Testa se a mensagem para 'book.title.empty' é retornada corretamente.
     */
    @Test
    @DisplayName("Teste recuperação de mensagem - Título do Livro Obrigatório")
    void testGetMessage_BookTitleEmpty() {
        String message = messageService.getMessage("book.title.empty");
        assertThat(message).isEqualTo("Título é obrigatório!");
    }

    /**
     * Testa se a mensagem para 'book.title.size' é retornada corretamente.
     */
    @Test
    @DisplayName("Teste recuperação de mensagem - Tamanho do Título do Livro Excedido")
    void testGetMessage_BookTitleSizeExceeded() {
        String message = messageService.getMessage("book.title.size");
        assertThat(message).isEqualTo("Título não pode ter mais de 100 caracteres.");
    }

    /**
     * Testa se a mensagem para 'book.author.empty' é retornada corretamente.
     */
    @Test
    @DisplayName("Teste recuperação de mensagem - Autor do Livro Obrigatório")
    void testGetMessage_BookAuthorEmpty() {
        String message = messageService.getMessage("book.author.empty");
        assertThat(message).isEqualTo("Autor é obrigatório!");
    }

    /**
     * Testa se a mensagem para 'book.author.size' é retornada corretamente.
     */
    @Test
    @DisplayName("Teste recuperação de mensagem - Tamanho do Autor do Livro Excedido")
    void testGetMessage_BookAuthorSizeExceeded() {
        String message = messageService.getMessage("book.author.size");
        assertThat(message).isEqualTo("Autor não pode ter mais de 50 caracteres.");
    }
}