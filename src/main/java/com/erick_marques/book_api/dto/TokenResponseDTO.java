package com.erick_marques.book_api.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDTO {
    private String token;
    private String type;
    private Long expiresIn;
    private LocalDateTime issuedAt;

    public TokenResponseDTO(String token){
        this.token     = token;
        this.type      = "Bearer";
        this.expiresIn = 3600L;
        this.issuedAt  = LocalDateTime.now();
    }
}
