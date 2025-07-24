package com.pos.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pos.model.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    
    Optional<Venta> findByFolio(String folio);
    boolean existsByFolio(String folio);
    boolean existsByFolioAndIdNot(String folio, Long id);
    //Consultar el ultimo folio para crear el siguiente
    @Query(value = "SELECT (REGEXP_MATCHES(folio, '\\d+'))[1]::INTEGER " +
                   "FROM ventas " +
                   "ORDER BY id DESC " +
                   "LIMIT 1", nativeQuery = true)
    Integer findLastFolioNumber();
    Page<Venta> findAll(Pageable pageable);
}
