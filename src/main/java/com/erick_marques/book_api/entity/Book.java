package com.erick_marques.book_api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)
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
     * Data de criação do registro do livro.
     * Este campo é gerenciado automaticamente e não pode ser atualizado.
     */
    @CreatedDate
    @Column(name = "dt_criacao", updatable = false)
    private LocalDateTime createdDate;

    /**
     * Data de última atualização do registro do livro.
     * Este campo é gerenciado automaticamente.
     */
    @LastModifiedDate
    @Column(name = "dt_atualizacao")
    private LocalDateTime lastModifiedDate;

    /**
     * Construtor que cria uma entidade Book a partir de um DTO.
     *
     * @param dto o DTO contendo os dados do livro.
     */
    public Book(BookRequestDTO dto) {
        this.title = dto.getTitle();
        this.author = dto.getAuthor();
    }
}