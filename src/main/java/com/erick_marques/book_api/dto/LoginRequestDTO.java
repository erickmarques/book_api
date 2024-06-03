package com.erick_marques.book_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa um DTO de requisiçao POST para o usuário ser autenticado.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "{user.login.empty}")
    @Size(max = 100, message = "{user.login.size}")
    private String login;

    @NotBlank(message = "{user.password.empty}")
    @Size(max = 100, message = "{user.password.size}")
    private String password;
}

