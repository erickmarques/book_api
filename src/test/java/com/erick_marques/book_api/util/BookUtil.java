package com.erick_marques.book_api.util;

import java.time.LocalDateTime;

import com.erick_marques.book_api.dto.BookRequestDTO;
import com.erick_marques.book_api.dto.BookResponseDTO;
import com.erick_marques.book_api.entity.Book;

public class BookUtil {

    public static final Long ID_DEFAULT       = 1L;
    public static final Long ID_NEGATIVE      = -9999L;
    public static final Long ID_NOT_FOUND     = 99999L;
    public static final String TITLE_DEFAULT  = "Pai Rico, Pai Pobre";
    public static final String AUTHOR_DEFAULT = "Robert Kiyosaki";

    public static Book createBookDefault(){
        return new Book(TITLE_DEFAULT, AUTHOR_DEFAULT);
    }

    public static BookRequestDTO createBookRequestDtoDefault(){
        return new BookRequestDTO(TITLE_DEFAULT, AUTHOR_DEFAULT);
    }

    public static BookResponseDTO createBookResponseDtoDefault(){
        return new BookResponseDTO(ID_DEFAULT, TITLE_DEFAULT, AUTHOR_DEFAULT, 0L, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
    }
    

    
}
