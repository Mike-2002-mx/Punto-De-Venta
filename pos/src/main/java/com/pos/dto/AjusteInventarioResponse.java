package com.pos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AjusteInventarioResponse {
    
    private Long id;
    private String folio;
    private String motivo;
    private LocalDateTime fecha;
    private BigDecimal totalNegativo;
    private BigDecimal totalPositivo;
    private BigDecimal totalGeneral;
    private List<ProductoAjustadoResponse> productosVendidos;
}
