package com.pos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pos.model.DetallesVenta;

public interface DetallesVentaRepository extends JpaRepository<DetallesVenta, Long> {
    
    @Query("SELECT dv FROM DetallesVenta dv WHERE dv.venta.id = :idVenta")
    List<DetallesVenta> findByIdVenta(@Param("idVenta") Long idVenta);

    List<DetallesVenta> findByVenta_IdIn(List<Long> idVentas);

}
