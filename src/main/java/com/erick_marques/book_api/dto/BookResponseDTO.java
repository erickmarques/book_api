package com.erick_marques.book_api.dto;

import java.time.LocalDateTime;

import com.erick_marques.book_api.entity.Book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Representa um DTO de retorno da entidade Livro.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {
    private Long id;
    private String title;
    private String author;
    private Long counter;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public BookResponseDTO(Book book){
        this.id                 = book.getId();
        this.title              = book.getTitle();
        this.author             = book.getAuthor();
        this.counter            = book.getCounter();
        this.createdDate        = book.getCreatedDate();
        this.lastModifiedDate   = book.getLastModifiedDate();
    }
}
