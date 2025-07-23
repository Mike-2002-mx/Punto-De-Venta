package com.pos.dto;

import java.math.BigDecimal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallesVentaResponse {
    
    private String descripcionProducto;
    private Integer cantidad;
    private BigDecimal precioVenta;
    private BigDecimal subtotal;

}
