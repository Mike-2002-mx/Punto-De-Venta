package com.pos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pos.model.DetallesVenta;
import com.pos.model.ProductoAjustado;

public interface ProductoAjustadoRepository extends JpaRepository<ProductoAjustado, Long> {
    
    @Query("SELECT pa FROM ProductoAjustado pa WHERE pa.ajusteInventario.id = :idAjuste")
    List<DetallesVenta> findByIdAjuste(@Param("idAjuste") Long idAjuste);
    List<DetallesVenta> findByAjusteInventario_IdIn(List<Long> idAjuste);
    
}
