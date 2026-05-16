package com.pidawe.controllers;

import com.pidawe.entities.Categoria;
import com.pidawe.repositories.CategoriaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    public CategoriaController(
            CategoriaRepository categoriaRepository
    ) {
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    @PostMapping
    public Categoria crear(
            @RequestBody Categoria categoria
    ) {
        return categoriaRepository.save(categoria);
    }
}