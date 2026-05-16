package com.pidawe.services;

import com.pidawe.dto.LoginRequest;
import com.pidawe.dto.LoginResponse;
import com.pidawe.entities.Usuario;
import com.pidawe.repositories.UsuarioRepository;
import com.pidawe.utils.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository repo;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthService(
            UsuarioRepository repo,
            BCryptPasswordEncoder encoder,
            JwtUtil jwtUtil
    ) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest request) {

        Usuario user = repo.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Usuario no existe")
                );

        if (!encoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new RuntimeException("Password incorrecta");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponse(token, user);
    }
}