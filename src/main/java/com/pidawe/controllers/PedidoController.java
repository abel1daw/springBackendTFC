package com.pidawe.controllers;

import com.pidawe.entities.*;
import com.pidawe.repositories.CarritoRepository;
import com.pidawe.services.PedidoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoService pedidoService;
    private final CarritoRepository carritoRepository;

    public PedidoController(
            PedidoService pedidoService,
            CarritoRepository carritoRepository
    ) {
        this.pedidoService = pedidoService;
        this.carritoRepository = carritoRepository;
    }

    @PostMapping("/crear/{carritoId}")
    public Pedido crearPedido(
            @PathVariable Long carritoId
    ) {

        Carrito carrito =
                carritoRepository.findById(carritoId)
                        .orElseThrow();

        return pedidoService.crearPedido(carrito);
    }
}