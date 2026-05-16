package com.pidawe.dto;

import com.pidawe.entities.Usuario;

public class LoginResponse {

    private String token;
    private Usuario usuario;

    public LoginResponse(String token, Usuario usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}