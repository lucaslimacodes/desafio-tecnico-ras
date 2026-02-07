package com.gruporas.tarifas.repository;

import com.gruporas.tarifas.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByNome(String nome);
}
