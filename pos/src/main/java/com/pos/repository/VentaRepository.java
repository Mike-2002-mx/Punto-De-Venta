package com.pos.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    //Obtener todas la ventas 
    Page<Venta> findAll(Pageable pageable);

    //buscar por fecha
    @Query("""
        SELECT v FROM Venta v WHERE v.fecha >= :fechaInicio AND v.fecha < :fechaFin
        """)
    Page<Venta> findVentasPorFecha(
        @Param("fechaInicio") LocalDateTime fechaInicio,
        @Param("fechaFin") LocalDateTime fechaFin,
        Pageable pageable
    );

    //Buscar ventas por fecha y idProducto
    @Query("""
        SELECT DISTINCT v FROM Venta v
        JOIN DetallesVenta d ON d.venta.id = v.id
        WHERE v.fecha >= :fechaInicio
        AND v.fecha < :fechaFin
        AND d.producto.id = :idProducto
    """)
    Page<Venta> findVentasPorFechaAndIdProducto(
        @Param("fechaInicio") LocalDateTime fechaInicio,
        @Param("fechaFin") LocalDateTime fechaFin,
        @Param("idProducto") Long idProducto,
        Pageable pageable
    );

}
