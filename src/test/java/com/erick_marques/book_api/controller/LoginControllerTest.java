package com.erick_marques.book_api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import com.erick_marques.book_api.dto.LoginRequestDTO;
import com.erick_marques.book_api.util.BookUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Classe de teste para {@link LoginController}.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes para o controller de Login")
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private LoginRequestDTO requestDTO;

    private final String BASE_URL = "/login";

    /**
     * Configura o ambiente de teste antes de cada teste.
     */
    @BeforeEach
    void setUp() {
        requestDTO = BookUtil.createLoginDefault();
    }

    /**
     * Teste para verificar o login com sucesso.
     *
     * @throws Exception se ocorrer um erro ao executar a solicitação.
     */
    @Test
    @DisplayName("Teste de integração do endpoint " + BASE_URL + " - login com sucesso.")
    void testLogin_Success() throws Exception {
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }

    /**
     * Teste para verificar o login com falha devido a credenciais inválidas.
     *
     * @throws Exception se ocorrer um erro ao executar a solicitação.
     */
    @Test
    @DisplayName("Teste de integração do endpoint " + BASE_URL + " - login com falha devido a credenciais inválidas.")
    void testLogin_Error() throws Exception {
        requestDTO.setPassword("-99999");

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isForbidden());
    } 
    
}
