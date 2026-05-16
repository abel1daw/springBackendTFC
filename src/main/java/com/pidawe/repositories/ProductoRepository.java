package com.pidawe.repositories;

import com.pidawe.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository
        extends JpaRepository<Producto, Long> {
}