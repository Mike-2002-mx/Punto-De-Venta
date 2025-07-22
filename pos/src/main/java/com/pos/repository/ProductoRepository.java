package com.pos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pos.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    Optional<Producto> findByCodigoBarras(String codigoBarras);
    boolean existsByCodigoBarras(String codigoBarras);
    boolean existsByCodigoBarrasAndIdNot(String codigoBarras, Long id);

    @Query(value = "SELECT * FROM productos WHERE unaccent(descripcion) ILIKE unaccent(CONCAT('%', :busqueda, '%')) OR unaccent(codigo_barras) ILIKE unaccent(CONCAT('%', :busqueda, '%'))", 
    nativeQuery = true)
    List<Producto> searchByText(@Param("busqueda") String busqueda);;


}
