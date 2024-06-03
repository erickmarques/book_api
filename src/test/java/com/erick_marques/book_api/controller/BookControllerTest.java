package com.erick_marques.book_api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.erick_marques.book_api.dto.BookRequestDTO;
import com.erick_marques.book_api.util.BookUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * Classe de teste de integração do Controlador Book.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes para o controller de Book")
public class BookControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/api/books";

    @Test
    @DisplayName("Teste de integração do GET " + BASE_URL + " - Deve pesquisar todos os livros por ordem do contador descrecente.")
    public void testGetAllBooks_Success() throws Exception {

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(10))))
                .andExpect(jsonPath("$[0].id").isNotEmpty())
                .andExpect(jsonPath("$[0].title").value("Clean Architecture"))
                .andExpect(jsonPath("$[0].author").value("Robert C. Martin"))
                .andExpect(jsonPath("$[0].createdDate").isNotEmpty())
                .andExpect(jsonPath("$[0].lastModifiedDate").isNotEmpty())
                .andExpect(jsonPath("$[0].counter").value(10));
    }

    @Test
    @DisplayName("Teste de integração do GET " + BASE_URL + "/{id} - Deve pesquisar o livro por ID.")
    public void testGetBookById_Success() throws Exception {

        mockMvc.perform(get(BASE_URL.concat("/{id}"), BookUtil.ID_DEFAULT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title").value("The Pragmatic Programmer"))
                .andExpect(jsonPath("$.author").value("Andrew Hunt"))
                .andExpect(jsonPath("$.createdDate").isNotEmpty())
                .andExpect(jsonPath("$.lastModifiedDate").isNotEmpty())
                .andExpect(jsonPath("$.counter").value(2));
    }

    @Test
    @DisplayName("Teste de integração do GET " + BASE_URL + "/{id} - Deve retornar NOT FOUND pois o ID não existe.")
    public void testGetBookById_IdNotFound() throws Exception {

        mockMvc.perform(get(BASE_URL.concat("/{id}"), BookUtil.ID_NOT_FOUND))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Teste de integração do GET " + BASE_URL + "/{id} - Deve retornar BAD REQUEST pois o ID tem valor negativo.")
    public void testGetBookById_IdNegative() throws Exception {

        mockMvc.perform(get(BASE_URL.concat("/{id}"), BookUtil.ID_NEGATIVE))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Teste de integração do POST " + BASE_URL + " - Deve criar um livro.")
    public void testCreateBook_Success() throws Exception{

        BookRequestDTO requestDTO = BookUtil.createBookRequestDtoDefault();

        mockMvc.perform(post(BASE_URL)
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

    @Test
    @DisplayName("Teste de integração do POST " + BASE_URL + " - Deve retornar BAD REQUEST pois os campos do livro está vazio.")
    public void testCreateBook_WithFieldsEmpty() throws Exception{

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new BookRequestDTO())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Teste de integração do POST " + BASE_URL + " - Deve retornar BAD REQUEST pois o campo título está vazio.")
    public void testCreateBook_WithTitleEmpty() throws Exception{

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTOWithTitleEmpty())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Teste de integração do POST " + BASE_URL + " - Deve retornar BAD REQUEST pois o campo autor está vazio.")
    public void testCreateBook_WithAuthorEmpty() throws Exception{

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTOWithAuthorEmpty())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Teste de integração do POST " + BASE_URL + " - Deve atualizar um livro.")
    public void testUpdateBook_Success() throws Exception{

        BookRequestDTO requestDTO = BookUtil.createBookRequestDtoDefault();

        mockMvc.perform(put(BASE_URL.concat("/{id}"), BookUtil.ID_DEFAULT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title").value(BookUtil.TITLE_DEFAULT))
                .andExpect(jsonPath("$.author").value(BookUtil.AUTHOR_DEFAULT))
                .andExpect(jsonPath("$.createdDate").isNotEmpty())
                .andExpect(jsonPath("$.lastModifiedDate").isNotEmpty());
    }

    @Test
    @DisplayName("Teste de integração do PUT " + BASE_URL + "/{id} - Deve retornar NOT FOUND pois o ID não existe.")
    public void testUpdateBook_IdNotFound() throws Exception{

        BookRequestDTO requestDTO = BookUtil.createBookRequestDtoDefault();

        mockMvc.perform(put(BASE_URL.concat("/{id}"), BookUtil.ID_NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Teste de integração do PUT " + BASE_URL + "/{id} - Deve retornar BAD REQUEST pois o ID tem valor negativo.")
    public void testUpdateBook_IdNegative() throws Exception{

        BookRequestDTO requestDTO = BookUtil.createBookRequestDtoDefault();

        mockMvc.perform(put(BASE_URL.concat("/{id}"), BookUtil.ID_NEGATIVE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Teste de integração do PUT " + BASE_URL + "/{id} - Deve retornar BAD REQUEST pois os campos estão vazios.")
    public void testUpdateBook_WithFieldsEmpty() throws Exception{

        mockMvc.perform(put(BASE_URL.concat("/{id}"), BookUtil.ID_DEFAULT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new BookRequestDTO())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Teste de integração do PUT " + BASE_URL + "/{id} - Deve retornar BAD REQUEST pois o título está vazio.")
    public void testUpdateBook_WithTitleEmpty() throws Exception{

        mockMvc.perform(put(BASE_URL.concat("/{id}"), BookUtil.ID_DEFAULT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTOWithTitleEmpty())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Teste de integração do PUT " + BASE_URL + "/{id} - Deve retornar BAD REQUEST pois os campos estão vazios.")
    public void testUpdateBook_WithAuthorEmpty() throws Exception{

        mockMvc.perform(put(BASE_URL.concat("/{id}"), BookUtil.ID_DEFAULT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTOWithAuthorEmpty())))
                .andExpect(status().isBadRequest());
    }

    private BookRequestDTO createDTOWithTitleEmpty(){
        return new BookRequestDTO(BookUtil.TITLE_DEFAULT, null);
    }

    private BookRequestDTO createDTOWithAuthorEmpty(){
        return new BookRequestDTO(null, BookUtil.AUTHOR_DEFAULT);
    }


}