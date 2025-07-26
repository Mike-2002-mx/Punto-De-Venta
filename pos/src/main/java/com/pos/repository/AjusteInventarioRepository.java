package com.pos.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pos.model.AjusteInventario;

@Repository
public interface AjusteInventarioRepository extends JpaRepository<AjusteInventario, Long>  {
    
    Optional<AjusteInventario> findByFolio(String folio);
    boolean existsByFolio(String folio);
    boolean existsByFolioAndIdNot(String folio, Long id);
    Page<AjusteInventario> findAll(Pageable pageable);

    //Consultar el ultimo folio para crear el siguiente
    @Query(value = "SELECT (REGEXP_MATCHES(folio, '\\d+'))[1]::INTEGER " +
                   "FROM ajustesInventario " +
                   "ORDER BY id DESC " +
                   "LIMIT 1", nativeQuery = true)
    Integer findLastFolioNumber();
}
