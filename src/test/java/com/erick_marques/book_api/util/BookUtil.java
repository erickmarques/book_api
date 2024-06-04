package com.erick_marques.book_api.util;


import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.erick_marques.book_api.dto.BookRequestDTO;
import com.erick_marques.book_api.dto.LoginRequestDTO;
import com.erick_marques.book_api.entity.Book;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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
    
    public static String getToken(MockMvc mockMvc, ObjectMapper objectMapper) {
        try {
            MvcResult result = mockMvc.perform(post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createLoginDefault())))
                    .andReturn();
            
            String jsonResponse = result.getResponse().getContentAsString();
            
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            String token = jsonNode.get("token").asText();
    
            return token;
        } catch (Exception e) {
            return null;
        }
    }
    

    public static LoginRequestDTO createLoginDefault(){
        return new LoginRequestDTO("erick.marques.andrade@gmail.com", "123");
    }
    
}
