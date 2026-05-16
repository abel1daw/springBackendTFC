package com.pidawe.controllers;

import com.pidawe.entities.Rol;
import com.pidawe.entities.Usuario;
import com.pidawe.repositories.UsuarioRepository;
import com.pidawe.services.UsuarioService;
import com.pidawe.utils.JwtUtil;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

	private final JwtUtil jwtUtil;
	private final UsuarioRepository usuarioRepository;
	
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService, JwtUtil jwtUtil, UsuarioRepository usuarioRepository ) {
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
		this.usuarioService = usuarioService;
    }

    @PostMapping
    public Usuario registrar(@RequestBody Usuario usuario) {
        return usuarioService.registrar(usuario);
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listar();
    }
    
   
    @GetMapping("/me")
    public Usuario getMe(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");

        String email = jwtUtil.extractEmail(token);

        return usuarioRepository.findByEmail(email)
                .orElseThrow();
    }
    @PutMapping("/me")
    public Usuario actualizarPerfil(

            @RequestHeader("Authorization")
            String authHeader,

            @RequestBody Usuario datosActualizados
    ) {

        String token =
                authHeader.replace("Bearer ", "");

        String email =
                jwtUtil.extractEmail(token);

        Usuario usuario =
                usuarioRepository.findByEmail(email)
                        .orElseThrow();

        usuario.setNombre(
                datosActualizados.getNombre()
        );

        usuario.setTelefono(
                datosActualizados.getTelefono()
        );

        return usuarioRepository.save(usuario);
    }
}