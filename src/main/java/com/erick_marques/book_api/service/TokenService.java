package com.erick_marques.book_api.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.erick_marques.book_api.entity.User;
import com.erick_marques.book_api.security.JwtProperties;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtProperties jwtProperties;

    /**
     * Gera um token JWT para um usuário específico.
     *
     * <p>Este método cria um token JWT usando o algoritmo HMAC256 e uma chave secreta obtida de 
     * {@code jwtProperties}. O token é assinado com "BookAPI" como emissor, o nome de usuário como sujeito, 
     * e inclui uma reivindicação personalizada com o ID do usuário. O token expira em 30 minutos a partir do momento de sua criação.</p>
     *
     * @param user o objeto {@code User} para o qual o token JWT será gerado
     * @return uma string representando o token JWT gerado
     * @throws JWTCreationException se ocorrer um erro durante a criação do token JWT
     */
    public String gerarToken(User user) {
        return JWT.create()
                .withIssuer("BookAPI")
                .withSubject(user.getUsername())
                .withClaim("id", user.getId())
                .withExpiresAt(LocalDateTime.now()
                        .plusMinutes(1)
                        .toInstant(ZoneOffset.of("-03:00"))
                ).sign(Algorithm.HMAC256(jwtProperties.getSecret()));
    }


    /**
     * Recupera o sujeito de um token JWT.
     *
     * <p>Este método verifica o token JWT fornecido usando o algoritmo HMAC256 e uma chave secreta 
     * obtida de {@code jwtProperties}. O token deve ter "BookAPI" como emissor. 
     * Se o token for verificado com sucesso, o sujeito contido no token é retornado.</p>
     *
     * @param token o token JWT do qual o sujeito será extraído
     * @return o sujeito contido no token JWT
     * @throws JWTVerificationException se o token for inválido ou a verificação falhar
     */
    public String getSubject(String token) {

           return JWT.require(Algorithm.HMAC256(jwtProperties.getSecret()))
                    .withIssuer("BookAPI")
                    .build()
                    .verify(token)
                    .getSubject();
        
    }
}
