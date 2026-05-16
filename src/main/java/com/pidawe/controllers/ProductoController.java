package com.pidawe.controllers;

import com.pidawe.entities.Producto;
import com.pidawe.repositories.ProductoRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoRepository productoRepository;

    public ProductoController(
            ProductoRepository productoRepository
    ) {
        this.productoRepository = productoRepository;
    }

    @GetMapping
    public Page<Producto> listar(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size
    ) {

    	Pageable pageable =
    	        PageRequest.of(page, size,
    	                Sort.by("id").ascending());

        return productoRepository.findAll(pageable);
    }

    @PostMapping
    public Producto crear(
            @RequestBody Producto producto
    ) {
        return productoRepository.save(producto);
    }
    
    
}