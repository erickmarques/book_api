package com.erick_marques.book_api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.erick_marques.book_api.entity.Book;
import java.util.List;

/**
 * Classe de teste para o repositório de Book.
 */
@DataJpaTest
@DisplayName("Testes do Repositório Book")
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    /**
     * Teste para pesquisar por ordem do contador decrescente todos os livros.
     */
    @Test
    @DisplayName("Teste para pesquisar por ordem do contador descrente todos os livros")
    public void testFindAllByOrderByCounterDesc(){
        List<Book> books = repository.findAllByOrderByCounterDesc();

        // Verificação
        assertNotNull(books, "A lista de livros não deve ser nula");
        assertEquals(10, books.size(), "Deveriam ser retornados 10 livros(inseridos no V2__Create_Data_Book.sql)");

        Long previousCounter = Long.MAX_VALUE;
        for (Book book : books) {
            assertTrue(book.getCounter() <= previousCounter, "Os livros devem estar ordenados por contador de forma decrescente");
            previousCounter = book.getCounter();
        }
    }
}
