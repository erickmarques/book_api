package com.erick_marques.book_api.service;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.erick_marques.book_api.dto.BookRequestDTO;
import com.erick_marques.book_api.dto.BookResponseDTO;
import com.erick_marques.book_api.entity.Book;
import com.erick_marques.book_api.repository.BookRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Classe de serviço para gerenciar livros.
 */
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    private final MessageSource messageSource;

    /**
     * Recupera todos os livros do repositório.
     *
     * @return uma lista de BookResponseDTO contendo todos os livros.
     */
    public List<BookResponseDTO> findAllByOrderByCounterDesc() {
        return repository.findAllByOrderByCounterDesc()
                         .stream()
                         .map(BookResponseDTO::new)
                         .collect(Collectors.toList());
    }

    /**
     * Recupera um livro pelo seu ID.
     *
     * @param id o ID do livro a ser recuperado.
     * @return um BookResponseDTO contendo os dados do livro.
     * @throws ResponseStatusException se o livro não for encontrado.
     */
    public BookResponseDTO getBookById(Long id) {
        validateId(id);
        return new BookResponseDTO(getBook(id));
    }

    /**
     * Salva um novo livro ou atualiza um livro existente no repositório.
     *
     * @param dto os dados do livro a ser salvo ou atualizado.
     * @return um BookResponseDTO contendo os dados do livro salvo.
     */
    @Transactional
    public BookResponseDTO saveBook(BookRequestDTO dto) {
        Book savedBook = repository.save(new Book(dto));
        return new BookResponseDTO(savedBook);
    }

    /**
     * Atualiza um livro existente no repositório.
     *
     * @param id o ID do livro a ser atualizado.
     * @param bookRequestDTO os dados atualizados do livro.
     * @return um BookResponseDTO contendo os dados do livro atualizado.
     * @throws ResponseStatusException se o livro não for encontrado.
     */
    @Transactional
    public BookResponseDTO updateBook(Long id, BookRequestDTO bookRequestDTO) {
        validateId(id);
        Book existingBook = getBook(id);

        existingBook.setTitle(bookRequestDTO.getTitle());
        existingBook.setAuthor(bookRequestDTO.getAuthor());
        existingBook.setLastModifiedDate(LocalDateTime.now());

        return new BookResponseDTO(repository.save(existingBook));
    }

    /**
     * Exclui um livro pelo seu ID.
     *
     * @param id o ID do livro a ser excluído.
     */
    @Transactional
    public void deleteBook(Long id) {
        validateId(id);
        repository.deleteById(id);
    }

    /**
     * Recupera um livro pelo seu ID do repositório.
     *
     * @param id o ID do livro a ser recuperado.
     * @return a entidade Book.
     * @throws ResponseStatusException se o livro não for encontrado.
     */
    public Book getBook(Long id) {
        return repository.findById(id)
                         .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, getMessage("book.notFound", id)));
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

    /**
     * Valida o ID do livro fornecido.
     *
     * @param id o ID a ser validado.
     * @throws ResponseStatusException se o ID for nulo ou inválido.
     */
    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, getMessage("book.id.invalid", id));
        }
    }

}
