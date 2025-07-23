package com.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pos.model.DetallesVenta;

public interface DetallesVentaRepository extends JpaRepository<DetallesVenta, Long> {
    
}
