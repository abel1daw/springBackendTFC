package com.pidawe.services;

import com.pidawe.entities.*;
import com.pidawe.repositories.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final CarritoRepository carritoRepository;

    public PedidoService(
            PedidoRepository pedidoRepository,
            CarritoRepository carritoRepository
    ) {
        this.pedidoRepository = pedidoRepository;
        this.carritoRepository = carritoRepository;
    }

    public Pedido crearPedido(Carrito carrito) {

        Pedido pedido = new Pedido();

        pedido.setUsuario(carrito.getUsuario());

        pedido.setEstado(EstadoPedido.PENDIENTE);

        BigDecimal total = carrito.getItems()
                .stream()
                .map(CarritoItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        pedido.setTotal(total);

        Pedido saved = pedidoRepository.save(pedido);

        carrito.getItems().clear();

        carritoRepository.save(carrito);

        return saved;
    }
}