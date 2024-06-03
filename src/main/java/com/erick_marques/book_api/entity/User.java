package com.erick_marques.book_api.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Entidade que representa um usuário (acesso ao sistema).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class User implements UserDetails{
    
    /**
     * Identificador único do usuário.
     */
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_usuario", nullable = false)
    private Long id;
    
    /**
     * Login do usuário.
     * Este campo não pode ser nulo e tem um tamanho máximo de 100 caracteres.
     */
    @Column(name = "ds_login", nullable = false, length = 100)
    private String login;

    /**
     * Senha do usuário.
     * Este campo não pode ser nulo e tem um tamanho máximo de 200 caracteres.
     */
    @Column(name = "ds_senha", nullable = false, length = 200)
    private String password;

    /**
     * Papel do usuário.
     * Este campo não pode ser nulo e tem um tamanho máximo de 20 caracteres.
     */
    @Column(name = "ds_papel", nullable = false, length = 20)
    private String role;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
