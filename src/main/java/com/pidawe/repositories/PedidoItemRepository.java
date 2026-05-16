package com.pidawe.repositories;

import com.pidawe.entities.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoItemRepository
        extends JpaRepository<PedidoItem, Long> {
}