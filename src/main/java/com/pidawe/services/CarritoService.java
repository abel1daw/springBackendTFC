package com.pidawe.services;

import com.pidawe.entities.Carrito;
import com.pidawe.entities.CarritoItem;
import com.pidawe.entities.Producto;
import com.pidawe.entities.Usuario;
import com.pidawe.repositories.CarritoItemRepository;
import com.pidawe.repositories.CarritoRepository;
import com.pidawe.repositories.ProductoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final CarritoItemRepository itemRepository;
    private final ProductoRepository productoRepository;

    public CarritoService(
            CarritoRepository carritoRepository,
            CarritoItemRepository itemRepository,
            ProductoRepository productoRepository
    ) {
        this.carritoRepository = carritoRepository;
        this.itemRepository = itemRepository;
        this.productoRepository = productoRepository;
    }

    public List<Carrito> listar() {
        return carritoRepository.findAll();
    }

    public Carrito crear(Carrito carrito) {
        return carritoRepository.save(carrito);
    }
    
    public Carrito obtenerPorUsuario(
            Long usuarioId
    ) {

        return carritoRepository
                .findByUsuarioId(usuarioId)
                .orElseThrow();
    }
    
    

    public CarritoItem agregarProducto(
            Long carritoId,
            Long productoId,
            Integer cantidad
    ) {

        Carrito carrito =
                carritoRepository.findById(carritoId)
                        .orElseThrow();

        Producto producto =
                productoRepository.findById(productoId)
                        .orElseThrow();

        CarritoItem item = itemRepository
                .findByCarritoIdAndProductoId(carritoId, productoId)
                .orElse(null);

        if (item != null) {

            // ✔ SI YA EXISTE → ACTUALIZAR

            item.setCantidad(item.getCantidad() + cantidad);

            item.setSubtotal(
                    producto.getPrecio()
                            .multiply(
                                    BigDecimal.valueOf(item.getCantidad())
                            )
            );

            return itemRepository.save(item);
        }

        // ✔ SI NO EXISTE → CREAR

        CarritoItem nuevo = new CarritoItem();

        nuevo.setCarrito(carrito);
        nuevo.setProducto(producto);
        nuevo.setCantidad(cantidad);

        nuevo.setSubtotal(
                producto.getPrecio()
                        .multiply(BigDecimal.valueOf(cantidad))
        );

        return itemRepository.save(nuevo);
    }
    
    public CarritoItem actualizarCantidad(Long itemId, Integer cantidad) {

        CarritoItem item = itemRepository.findById(itemId)
                .orElseThrow();

        Producto producto = item.getProducto();

        item.setCantidad(cantidad);

        item.setSubtotal(
                producto.getPrecio()
                        .multiply(BigDecimal.valueOf(cantidad))
        );

        return itemRepository.save(item);
    }
    
    public Carrito obtenerOCrearCarrito(Usuario usuario) {

        return carritoRepository.findByUsuarioId(usuario.getId())
                .orElseGet(() -> {

                    Carrito carrito = new Carrito();

                    carrito.setUsuario(usuario);

                    return carritoRepository.save(carrito);
                });
    }
    public void eliminarItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }
}