package com.pos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pos.model.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    
    Optional<Venta> findByFolio(String folio);
    boolean existsByFolio(String folio);
    boolean existsByFolioAndIdNot(String folio, Long id);
}
