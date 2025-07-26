package com.pos.dto;

import java.math.BigDecimal;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoAjustadoResponse {
    
    private String descripcionProducto;
    private Integer existenciaAnterior;
    private Integer nuevaExistencia;
    private Integer diferencia;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}
