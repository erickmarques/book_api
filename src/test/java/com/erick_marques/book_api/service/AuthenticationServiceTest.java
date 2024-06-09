package com.erick_marques.book_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.erick_marques.book_api.util.BookUtil;

/**
 * Teste de classe {@link AuthenticationService}.
 */
@SpringBootTest
@DisplayName("Testes para o serviço de Autenticação de usuário")
class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MessageService messageService;

    /**
     * Testa se um usuário válido é encontrado pelo nome de usuário.
     */
    @Test
    @DisplayName("Teste carregar usuário pelo nome de usuário - Usuário Encontrado")
    void testLoadUserByUsername_UserFound() {
        UserDetails result = authenticationService.loadUserByUsername(BookUtil.EMAIL_DEFAULT);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(BookUtil.EMAIL_DEFAULT);
    }


    /**
     * Testa se uma exceção é lançada quando um usuário não é encontrado.
     */
    @Test
    @DisplayName("Teste carregar usuário pelo nome de usuário - Usuário Não Encontrado")
    void testLoadUserByUsername_UserNotFound() {
        validateException("nonExistentUser");
    }

    /**
     * Testa se uma exceção é lançada quando o nome de usuário está vazio.
     */
    @Test
    @DisplayName("Teste carregar usuário pelo nome de usuário - Nome de Usuário Vazio")
    void testLoadUserByUsername_EmptyUsername() {
        validateException("");
    }

    /**
     * Testa se uma exceção é lançada quando o nome de usuário é nulo.
     */
    @Test
    @DisplayName("Teste carregar usuário pelo nome de usuário - Nome de Usuário Nulo")
    void testLoadUserByUsername_NullUsername() {
        validateException(null);
    }

    /**
     * Valida a exceção de busca de usuário não encontrado.
     * 
     * @param userName
     */
    private void validateException(String userName){
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            authenticationService.loadUserByUsername(userName);
        });

        assertThat(exception.getMessage()).isEqualTo(messageService.getMessage("user.loginInvalid"));
    }
}


