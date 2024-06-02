package com.erick_marques.book_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa um DTO de requisiçao (insert/update) da entidade Livro.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDTO {

    @NotBlank(message = "{book.title.empty}")
    @Size(max = 100, message = "{book.title.size}")
    private String title;

    @NotBlank(message = "{book.author.empty}")
    @Size(max = 100, message = "{book.author.size}")
    private String author;
}
