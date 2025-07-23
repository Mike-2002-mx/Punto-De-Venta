package com.pos.dto;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaResponse {

    private Long id;
    private String folio;
    private LocalDateTime fecha;
    private BigDecimal total;
    private BigDecimal pagoCon;
    private BigDecimal cambio;
    private List<DetallesVentaResponse> productosVendidos;
}
