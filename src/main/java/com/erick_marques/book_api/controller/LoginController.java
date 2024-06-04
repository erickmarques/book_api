package com.erick_marques.book_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.erick_marques.book_api.dto.LoginRequestDTO;
import com.erick_marques.book_api.dto.TokenResponseDTO;
import com.erick_marques.book_api.entity.User;
import com.erick_marques.book_api.service.TokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {

        //try {
        
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                        dto.getLogin(),
                        dto.getPassword()
                    );

            Authentication authenticate = this.authenticationManager
                    .authenticate(usernamePasswordAuthenticationToken);

            var user = (User) authenticate.getPrincipal();

            String token = tokenService.gerarToken(user);

            return ResponseEntity.ok(new TokenResponseDTO(token));

        /* } catch (BadCredentialsException e) {
            System.out.println("erro aqui 111");
            e.printStackTrace();
            return null;
        }  catch (Exception e) {
            System.out.println("erro aqui 333");
            e.printStackTrace();
            return null;
        }*/
    }
}