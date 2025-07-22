package com.pos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    Optional<Producto> findByCodigoBarras(String codigoBarras);
    boolean existsByCodigoBarras(String codigoBarras);
    boolean existsByCodigoBarrasAndIdNot(String codigoBarras, Long id);
}
