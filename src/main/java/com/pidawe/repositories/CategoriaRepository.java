package com.pidawe.repositories;

import com.pidawe.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository
        extends JpaRepository<Categoria, Long> {
}