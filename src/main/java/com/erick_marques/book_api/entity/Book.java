package com.erick_marques.book_api.entity;

import jakarta.persistence.*;
import lombok.*;

import com.erick_marques.book_api.dto.BookRequestDTO;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entidade representando um Livro.
 * Esta entidade é mapeada para a tabela 'livro' no banco de dados.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "livro")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único do livro.
     */
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_livro", nullable = false)
    private Long id;

    /**
     * Título do livro.
     * Este campo não pode ser nulo e tem um tamanho máximo de 100 caracteres.
     */
    @Column(name = "ds_titulo", nullable = false, length = 100)
    private String title;

    /**
     * Autor do livro.
     * Este campo não pode ser nulo e tem um tamanho máximo de 50 caracteres.
     */
    @Column(name = "nm_autor", nullable = false, length = 50)
    private String author;

    /**
     * Contador do livro, incrementado a cada chamada na API.
     */
    @Column(name = "nr_contador")
    private Long counter = 0L;

    /**
     * Data de criação do registro do livro.
     * Este campo é gerenciado automaticamente e não pode ser atualizado.
     */
    @Column(name = "dt_criacao", updatable = false)
    private LocalDateTime createdDate;

    /**
     * Data de última atualização do registro do livro.
     * Este campo é gerenciado automaticamente.
     */
    @Column(name = "dt_atualizacao")
    private LocalDateTime lastModifiedDate;


    /**
     * Construtor que cria uma entidade Book a partir de um DTO.
     *
     * @param dto o DTO contendo os dados do livro.
     */
    public Book(BookRequestDTO dto) {
        this.title       = dto.getTitle();
        this.author      = dto.getAuthor();
        this.createdDate = LocalDateTime.now();
    }

    /**
     * Construtor que cria uma entidade Book a partir dos parametros.
     *
     * @param title Título do livro.
     * * @param author Autor do livro.
     */
    public Book(String title, String author) {
        this.title  = title;
        this.author = author;
    }
}
