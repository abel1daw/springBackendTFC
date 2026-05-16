package com.pidawe.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pidawe.entities.CarritoItem;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {

    Optional<CarritoItem> findByCarritoIdAndProductoId(
            Long carritoId,
            Long productoId
    );
}