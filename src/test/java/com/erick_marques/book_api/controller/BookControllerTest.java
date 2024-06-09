package com.erick_marques.book_api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.erick_marques.book_api.dto.BookRequestDTO;
import com.erick_marques.book_api.util.BookUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * Classe de teste para {@link BookController}.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes para o controller de Book")
@Transactional
class BookControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/api/books";

    private String token;

    /**
     * Configura o ambiente de teste antes de cada teste.
     */
    @BeforeEach
    void setUp() {
        token = BookUtil.getToken(mockMvc, objectMapper);
    }

    /**
     * Teste de integração do GET /api/books
     * Deve pesquisar todos os livros por ordem do contador descrecente.
     */
    @Test
    @DisplayName("Teste de integração do GET " + BASE_URL + " - Deve pesquisar todos os livros por ordem do contador descrecente.")
    void testGetAllBooks_Success() throws Exception {

        mockMvc.perform(get(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(10))))
                .andExpect(jsonPath("$[0].id").isNotEmpty())
                .andExpect(jsonPath("$[0].title").value("Clean Architecture"))
                .andExpect(jsonPath("$[0].author").value("Robert C. Martin"))
                .andExpect(jsonPath("$[0].createdDate").isNotEmpty())
                .andExpect(jsonPath("$[0].lastModifiedDate").isNotEmpty())
                .andExpect(jsonPath("$[0].counter").value(10));
    }

    /**
     * Teste de integração do GET /api/books/{id}
     * Deve pesquisar o livro por ID.
     */
    @Test
    @DisplayName("Teste de integração do GET " + BASE_URL + "/{id} - Deve pesquisar o livro por ID.")
    void testGetBookById_Success() throws Exception {

        mockMvc.perform(get(BASE_URL.concat("/{id}"), BookUtil.ID_DEFAULT)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title").value("The Pragmatic Programmer"))
                .andExpect(jsonPath("$.author").value("Andrew Hunt"))
                .andExpect(jsonPath("$.createdDate").isNotEmpty())
                .andExpect(jsonPath("$.lastModifiedDate").isNotEmpty())
                .andExpect(jsonPath("$.counter").value(2));
    }

    /**
     * Teste de integração do GET /api/books/{id}
     * Deve retornar NOT FOUND pois o ID não existe.
     */
    @Test
    @DisplayName("Teste de integração do GET " + BASE_URL + "/{id} - Deve retornar NOT FOUND pois o ID não existe.")
    void testGetBookById_IdNotFound() throws Exception {

        mockMvc.perform(get(BASE_URL.concat("/{id}"), BookUtil.ID_NOT_FOUND)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    /**
     * Teste de integração do GET /api/books/{id}
     * Deve retornar BAD REQUEST pois o ID tem valor negativo.
     */
    @Test
    @DisplayName("Teste de integração do GET " + BASE_URL + "/{id} - Deve retornar BAD REQUEST pois o ID tem valor negativo.")
    void testGetBookById_IdNegative() throws Exception {

        mockMvc.perform(get(BASE_URL.concat("/{id}"), BookUtil.ID_NEGATIVE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    /**
     * Teste de integração do POST /api/books
     * Deve criar um livro.
     */
    @Test
    @DisplayName("Teste de integração do POST " + BASE_URL + " - Deve criar um livro.")
    void testCreateBook_Success() throws Exception{

        BookRequestDTO requestDTO = BookUtil.createBookRequestDtoDefault();

        mockMvc.perform(post(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title").value(BookUtil.TITLE_DEFAULT))
                .andExpect(jsonPath("$.author").value(BookUtil.AUTHOR_DEFAULT))
                .andExpect(jsonPath("$.createdDate").isNotEmpty())
                .andExpect(jsonPath("$.lastModifiedDate").isEmpty())
                .andExpect(jsonPath("$.counter").value(0));
    }

    /**
     * Teste de integração do POST /api/books
     * Deve retornar BAD REQUEST pois os campos do livro estão vazios.
     */
    @Test
    @DisplayName("Teste de integração do POST " + BASE_URL + " - Deve retornar BAD REQUEST pois os campos do livro está vazio.")
    void testCreateBook_WithFieldsEmpty() throws Exception{

        mockMvc.perform(post(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new BookRequestDTO())))
                .andExpect(status().isBadRequest());
    }

    /**
     * Teste de integração do POST /api/books
     * Deve retornar BAD REQUEST pois o campo título está vazio.
     */
    @Test
    @DisplayName("Teste de integração do POST " + BASE_URL + " - Deve retornar BAD REQUEST pois o campo título está vazio.")
    void testCreateBook_WithTitleEmpty() throws Exception{

        mockMvc.perform(post(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTOWithTitleEmpty())))
                .andExpect(status().isBadRequest());
    }

    /**
     * Teste de integração do POST /api/books
     * Deve retornar BAD REQUEST pois o campo autor está vazio.
     */
    @Test
    @DisplayName("Teste de integração do POST " + BASE_URL + " - Deve retornar BAD REQUEST pois o campo autor está vazio.")
    void testCreateBook_WithAuthorEmpty() throws Exception{

        mockMvc.perform(post(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTOWithAuthorEmpty())))
                .andExpect(status().isBadRequest());
    }

    /**
     * Teste de integração do PUT /api/books/{id}
     * Deve atualizar um livro.
     */
    @Test
    @DisplayName("Teste de integração do POST " + BASE_URL + " - Deve atualizar um livro.")
    void testUpdateBook_Success() throws Exception{

        BookRequestDTO requestDTO = BookUtil.createBookRequestDtoDefault();

        mockMvc.perform(put(BASE_URL.concat("/{id}"), BookUtil.ID_DEFAULT)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title").value(BookUtil.TITLE_DEFAULT))
                .andExpect(jsonPath("$.author").value(BookUtil.AUTHOR_DEFAULT))
                .andExpect(jsonPath("$.createdDate").isNotEmpty())
                .andExpect(jsonPath("$.lastModifiedDate").isNotEmpty());
    }

    /**
     * Teste de integração do PUT /api/books/{id}
     * Deve retornar NOT FOUND pois o ID não existe.
     */
    @Test
    @DisplayName("Teste de integração do PUT " + BASE_URL + "/{id} - Deve retornar NOT FOUND pois o ID não existe.")
    void testUpdateBook_IdNotFound() throws Exception{

        BookRequestDTO requestDTO = BookUtil.createBookRequestDtoDefault();

        mockMvc.perform(put(BASE_URL.concat("/{id}"), BookUtil.ID_NOT_FOUND)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNotFound());
    }

    /**
     * Teste de integração do PUT /api/books/{id}
     * Deve retornar BAD REQUEST pois o ID tem valor negativo.
     */
    @Test
    @DisplayName("Teste de integração do PUT " + BASE_URL + "/{id} - Deve retornar BAD REQUEST pois o ID tem valor negativo.")
    void testUpdateBook_IdNegative() throws Exception{

        BookRequestDTO requestDTO = BookUtil.createBookRequestDtoDefault();

        mockMvc.perform(put(BASE_URL.concat("/{id}"), BookUtil.ID_NEGATIVE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    /**
     * Teste de integração do PUT /api/books/{id}
     * Deve retornar BAD REQUEST pois os campos estão vazios.
     */
    @Test
    @DisplayName("Teste de integração do PUT " + BASE_URL + "/{id} - Deve retornar BAD REQUEST pois os campos estão vazios.")
    void testUpdateBook_WithFieldsEmpty() throws Exception{

        mockMvc.perform(put(BASE_URL.concat("/{id}"), BookUtil.ID_DEFAULT)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new BookRequestDTO())))
                .andExpect(status().isBadRequest());
    }

    /**
     * Teste de integração do PUT /api/books/{id}
     * Deve retornar BAD REQUEST pois o título está vazio.
     */
    @Test
    @DisplayName("Teste de integração do PUT " + BASE_URL + "/{id} - Deve retornar BAD REQUEST pois o título está vazio.")
    void testUpdateBook_WithTitleEmpty() throws Exception{

        mockMvc.perform(put(BASE_URL.concat("/{id}"), BookUtil.ID_DEFAULT)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTOWithTitleEmpty())))
                .andExpect(status().isBadRequest());
    }

    /**
     * Teste de integração do PUT /api/books/{id}
     * Deve retornar BAD REQUEST pois o autor está vazio.
     */
    @Test
    @DisplayName("Teste de integração do PUT " + BASE_URL + "/{id} - Deve retornar BAD REQUEST pois o autor está vazio.")
    void testUpdateBook_WithAuthorEmpty() throws Exception{

        mockMvc.perform(put(BASE_URL.concat("/{id}"), BookUtil.ID_DEFAULT)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTOWithAuthorEmpty())))
                .andExpect(status().isBadRequest());
    }

    /**
     * Teste de integração do PUT /api/books/{id}
     * Deve retornar NO CONTENT após remover o livro.
     */
    @Test
    @DisplayName("Teste de integração do DELETE " + BASE_URL + "/{id} - Deve remover um livro existente.")
    void testDeleteBook_Success() throws Exception {
        mockMvc.perform(delete(BASE_URL.concat("/{id}"), 2L)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    /**
     * Teste de integração do DELETE /api/books/{id}
     * Deve retornar NOT FOUND pois o ID não existe.
     */
    @Test
    @DisplayName("Teste de integração do DELETE " + BASE_URL + "/{id} - Deve retornar NOT FOUND pois o ID não existe.")
    void testDeleteBook_IdNotFound() throws Exception {
        mockMvc.perform(delete(BASE_URL.concat("/{id}"), BookUtil.ID_NOT_FOUND)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    /**
     * Teste de integração do DELETE /api/books/{id}
     * Deve retornar BAD REQUEST pois o ID tem valor negativo.
     */
    @Test
    @DisplayName("Teste de integração do DELETE " + BASE_URL + "/{id} - Deve retornar BAD REQUEST pois o ID tem valor negativo.")
    void testDeleteBook_IdNegative() throws Exception {
        mockMvc.perform(delete(BASE_URL.concat("/{id}"), BookUtil.ID_NEGATIVE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    /**
     * Retorna o BookRequestDTO apenas com o título preenchido.
     * 
     * @return BookRequestDTO
     */
    private BookRequestDTO createDTOWithTitleEmpty(){
        return new BookRequestDTO(BookUtil.TITLE_DEFAULT, null);
    }


    /**
     * Retorna o BookRequestDTO apenas com o autor preenchido.
     * 
     * @return BookRequestDTO
     */
    private BookRequestDTO createDTOWithAuthorEmpty(){
        return new BookRequestDTO(null, BookUtil.AUTHOR_DEFAULT);
    }
}
