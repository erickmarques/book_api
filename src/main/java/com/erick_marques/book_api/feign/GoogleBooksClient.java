package com.erick_marques.book_api.feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.erick_marques.book_api.dto.GoogleBooksResponseDTO;

@FeignClient(name = "googleBooksClient", url = "https://www.googleapis.com/books/v1")
public interface GoogleBooksClient {

    @GetMapping("/volumes")
    GoogleBooksResponseDTO searchBooks(@RequestParam("q") String query, @RequestParam("key") String apiKey);
}

