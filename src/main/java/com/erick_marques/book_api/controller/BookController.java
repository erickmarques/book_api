package com.erick_marques.book_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erick_marques.book_api.dto.BookRequestDTO;
import com.erick_marques.book_api.dto.BookResponseDTO;
import com.erick_marques.book_api.dto.GoogleBooksResponseDTO;
import com.erick_marques.book_api.feign.GoogleBooksClient;
import com.erick_marques.book_api.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controlador para gerenciar livros.
 */
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final GoogleBooksClient googleBooksClient;

    @Value("${google.api.key}")
    private String apiKey;

    /**
     * Recupera todos os livros.
     *
     * @return uma ResponseEntity contendo uma lista de BookResponseDTO e HttpStatus OK.
     */
    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        List<BookResponseDTO> books = bookService.findAllByOrderByCounterDesc();
        return ResponseEntity.ok(books);
    }

    /**
     * Recupera um livro pelo seu ID.
     *
     * @param id o ID do livro a ser recuperado.
     * @return uma ResponseEntity contendo o BookResponseDTO e HttpStatus OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        BookResponseDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    /**
     * Cria um novo livro.
     *
     * @param dto o objeto de transferência de dados contendo os detalhes do livro a ser criado.
     * @return uma ResponseEntity contendo o BookResponseDTO criado e HttpStatus CREATED.
     */
    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookRequestDTO dto) {
        BookResponseDTO createdBook = bookService.saveBook(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    /**
     * Atualiza um livro existente.
     *
     * @param id  o ID do livro a ser atualizado.
     * @param dto o objeto de transferência de dados contendo os detalhes atualizados do livro.
     * @return uma ResponseEntity contendo o BookResponseDTO atualizado e HttpStatus OK.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequestDTO dto) {
        BookResponseDTO updatedBook = bookService.updateBook(id, dto);
        return ResponseEntity.ok(updatedBook);
    }

    /**
     * Exclui um livro pelo seu ID.
     *
     * @param id o ID do livro a ser excluído.
     * @return uma ResponseEntity com HttpStatus NO_CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    /*
     * Pesquisa livros a partir do título na API Google Books.
     * 
     * @param title o título de pesquisa do livro.
     * @return uma ResponseEntity contendo o GoogleBooksResponseDTO e HttpStatus OK.
     */
    @GetMapping("/google-books")
    public GoogleBooksResponseDTO getBookByIsbn(@RequestParam String title) {
        return googleBooksClient.searchBooks(title, apiKey);
    }
}
