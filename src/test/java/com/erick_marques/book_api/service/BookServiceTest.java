package com.erick_marques.book_api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.erick_marques.book_api.dto.BookRequestDTO;
import com.erick_marques.book_api.dto.BookResponseDTO;
import com.erick_marques.book_api.entity.Book;
import com.erick_marques.book_api.util.BookUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Locale;

/**
 * Testes para o serviço de Book.
 */
@SpringBootTest
@DisplayName("Testes para o serviço de Book")
@Transactional
public class BookServiceTest {

    @Autowired
    private BookService service;

    @Autowired
    private MessageSource messageSource;

    /**
     * Teste para recuperar todos os livros e convertê-los corretamente para BookResponseDTO.
     */
    @Test
    @DisplayName("Teste para recuperar todos os livros e convertê-los corretamente para BookResponseDTO.")
    public void testFindAllByOrderByCounterDesc() {
        List<BookResponseDTO> booksDto = service.findAllByOrderByCounterDesc();

        assertFalse(booksDto.isEmpty(), "A lista de livros não deve ser vazia");

        for (BookResponseDTO dto : booksDto) {
            validateEntityToDto(dto);
        }
    }

    /**
     * Teste para recuperar um livro pelo ID e convertê-lo corretamente para BookResponseDTO.
     */
    @Test
    @DisplayName("Teste para recuperar o livros pelo ID e convertê-lo corretamente para BookResponseDTO.")    
    public void testGetBookById_Success() {

        Book book                = service.getBook(BookUtil.ID_DEFAULT);
        Long bookCounter         = book.getCounter();
        BookResponseDTO response = service.getBookById(BookUtil.ID_DEFAULT);

        validateEntityToDto(response);
        assertEquals(response.getCounter(), bookCounter + 1); //testa da incrementação do contador.
    }

    /**
     * Teste ao realizar uma busca por ID inexistente para recuperar a exceção e mensagem.
     */
    @Test
    @DisplayName("Teste ao realizar uma busca por id inexistente para recuperar a exceção e mensagem.")
    public void testGetBookById_IdNotFound() {

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, 
            () -> service.getBookById(BookUtil.ID_NOT_FOUND));

        validateExceptionIdNotFound(exception);
    }

    /**
     * Teste ao realizar uma busca por ID negativo para recuperar a exceção e mensagem.
     */
    @Test
    @DisplayName("Teste ao realizar uma busca por id inexistente para recuperar a exceção e mensagem.")
    public void testGetBookById_IdNegativo() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, 
            () -> service.getBookById(BookUtil.ID_NEGATIVE));

        validateExceptionIdBadRequest(exception, BookUtil.ID_NEGATIVE);
    }

    /**
     * Teste para salvar um livro com sucesso e convertê-lo corretamente para BookResponseDTO.
     */
    @Test
    @DisplayName("Teste para criar um livro com sucesso e convertê-lo corretamente para BookResponseDTO.")
    public void testSaveBook_Success() {
        BookRequestDTO request = BookUtil.createBookRequestDtoDefault();
        BookResponseDTO response = service.saveBook(request);

        assertNull(response.getLastModifiedDate(), "Data de atualização não deve ser retornada!");
        validateEntityToDto(response);
    }

    /**
     * Teste para atualizar um livro com sucesso e convertê-lo corretamente para BookResponseDTO.
     */
    @Test
    @DisplayName("Teste para atualizar um livro com sucesso e convertê-lo corretamente para BookResponseDTO.")
    public void testUpdateBook_Success() {

        BookRequestDTO request = BookUtil.createBookRequestDtoDefault();
        BookResponseDTO response = service.updateBook(BookUtil.ID_DEFAULT, request);
        
        assertNotNull(response.getLastModifiedDate(), "Data de atualização deve ser retornada!");
        assertTrue(response.getLastModifiedDate().isAfter(response.getCreatedDate()), 
            "Data de atualização deve ser maior que a data de criação!");
        
        validateEntityToDto(response);
    }

    /**
     * Teste ao realizar um update por ID inexistente para recuperar a exceção e mensagem.
     */
    @Test
    @DisplayName("Teste ao realizar um update por id inexistente para recuperar a exceção e mensagem.")
    public void testUpdateBook_IdNotFound() {

        BookRequestDTO request = new BookRequestDTO();

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, 
            () -> service.updateBook(BookUtil.ID_NOT_FOUND, request));

        validateExceptionIdNotFound(exception);
    }

    /**
     * Teste ao realizar um update por ID negativo para recuperar a exceção e mensagem.
     */
    @Test
    @DisplayName("Teste ao realizar um update por id com valor negativo para recuperar a exceção e mensagem.")
    public void testUpdateBook_IdNegative() {

        BookRequestDTO request = new BookRequestDTO();

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, 
            () -> service.updateBook(BookUtil.ID_NEGATIVE, request));

        validateExceptionIdBadRequest(exception, BookUtil.ID_NEGATIVE);
    }

    /**
     * Teste para deletar um livro com sucesso.
     */
    @Test
    @DisplayName("Teste para deletar um livro com sucesso.")
    public void testDeleteBook_Success() {

        Long validId = 2L;
        service.deleteBook(validId);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, 
            () -> service.getBook(validId));

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(exception.getReason()).isEqualTo(getMessage("book.notFound", validId));
    }

    /**
     * Teste para deletar um livro com ID inexistente.
     */
    @Test
    @DisplayName("Teste para deletar um livro com ID inexistente.")
    public void testDeleteBook_IdNotFound() {
		
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, 
            () -> service.deleteBook(BookUtil.ID_NOT_FOUND));

        validateExceptionIdNotFound(exception);

    }

    /**
     * Teste para deletar um livro com ID negativo.
     */
    @Test
    @DisplayName("Teste para deletar um livro com ID com valor negativo.")
    public void testDeleteBook_IdNegative() {
		
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, 
            () -> service.deleteBook(BookUtil.ID_NEGATIVE));

        validateExceptionIdBadRequest(exception, BookUtil.ID_NEGATIVE);
    }

    /**
     * Valida a exceção de ID não encontrado.
     *
     * @param exception a exceção lançada.
     */
    private void validateExceptionIdNotFound(ResponseStatusException exception) {
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(exception.getReason()).isEqualTo(getMessage("book.notFound", BookUtil.ID_NOT_FOUND));
    }

    /**
     * Valida a exceção de ID inválido.
     *
     * @param exception a exceção lançada.
     * @param negativeId o ID negativo.
     */
    private void validateExceptionIdBadRequest(ResponseStatusException exception, Long negativeId) {
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getReason()).isEqualTo(getMessage("book.id.invalid", negativeId));
    }


    /**
     * Valida a conversão de Book para BookResponseDTO.
     *
     * @param dto o DTO a ser validado.
     */
    private void validateEntityToDto(BookResponseDTO dto) {
        Book book = service.getBook(dto.getId());

        assertNotNull(dto);
        assertNotNull(dto.getId(), "ID deve ser retornado!");
        assertNotNull(dto.getCreatedDate(), "Data de criação deve ser retornada!");
        
        assertEquals(book.getId(),      dto.getId());
        assertEquals(book.getTitle(),   dto.getTitle());
        assertEquals(book.getAuthor(),  dto.getAuthor());
    }

    /**
     * Recupera uma mensagem localizada para o código e argumentos fornecidos.
     *
     * @param code o código da mensagem.
     * @param args os argumentos para a mensagem.
     * @return a mensagem localizada.
     */
    private String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, Locale.getDefault());
    }
}
