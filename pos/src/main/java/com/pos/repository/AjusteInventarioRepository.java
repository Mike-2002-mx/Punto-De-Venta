package com.pos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.model.AjusteInventario;

@Repository
public interface AjusteInventarioRepository extends JpaRepository<AjusteInventario, Long>  {
    
    Optional<AjusteInventario> findByFolio(String folio);
    boolean existsByFolio(String folio);
    boolean existsByFolioAndIdNot(String folio, Long id);

}
