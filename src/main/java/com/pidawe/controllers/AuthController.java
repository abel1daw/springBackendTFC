package com.pidawe.controllers;

import com.pidawe.dto.LoginRequest;
import com.pidawe.dto.LoginResponse;
import com.pidawe.entities.Usuario;
import com.pidawe.services.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}