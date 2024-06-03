package com.erick_marques.book_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.erick_marques.book_api.entity.Book;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByOrderByCounterDesc();
}

