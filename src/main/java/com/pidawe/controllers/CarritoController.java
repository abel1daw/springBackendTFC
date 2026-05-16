package com.pidawe.controllers;

import com.pidawe.entities.Carrito;
import com.pidawe.entities.CarritoItem;
import com.pidawe.entities.Usuario;
import com.pidawe.repositories.CarritoRepository;
import com.pidawe.repositories.UsuarioRepository;
import com.pidawe.services.CarritoService;
import com.pidawe.utils.JwtUtil;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carritos")
@CrossOrigin(origins = "*")
public class CarritoController {
	private final JwtUtil jwtUtil;

	private final UsuarioRepository usuarioRepository;

	private final CarritoRepository carritoRepository;
    private final CarritoService carritoService;

    public CarritoController(
            CarritoService carritoService,
            JwtUtil jwtUtil,
            UsuarioRepository usuarioRepository,
            CarritoRepository carritoRepository
    ) {

        this.carritoService = carritoService;
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
        this.carritoRepository = carritoRepository;
    }

    @GetMapping
    public List<Carrito> listar() {
        return carritoService.listar();
    }

    @PostMapping
    public Carrito crear(
            @RequestBody Carrito carrito
    ) {
        return carritoService.crear(carrito);
    }

    @PostMapping("/me/agregar")
    public CarritoItem agregarProducto(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam Long productoId,
            @RequestParam Integer cantidad
    ) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token);

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow();

        Carrito carrito = carritoService.obtenerOCrearCarrito(usuario);

        return carritoService.agregarProducto(
                carrito.getId(),
                productoId,
                cantidad
        );
    }
    
    @DeleteMapping("/item/{itemId}")
    public void eliminarItem(
            @PathVariable Long itemId
    ) {
        carritoService.eliminarItem(itemId);
    }
    
    @PutMapping("/item/{itemId}")
    public CarritoItem actualizarCantidad(
            @PathVariable Long itemId,
            @RequestParam Integer cantidad
    ) {
        return carritoService.actualizarCantidad(itemId, cantidad);
    }
    
    @GetMapping("/me")
    public Carrito getMiCarrito(
            @RequestHeader("Authorization") String authHeader
    ) {

        String token = authHeader.replace("Bearer ", "");

        String email = jwtUtil.extractEmail(token);

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow();

        return carritoService.obtenerOCrearCarrito(usuario);
    }
}