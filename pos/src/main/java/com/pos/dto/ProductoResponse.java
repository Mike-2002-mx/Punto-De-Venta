package com.pos.dto;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProductoResponse {
    private Long id;
    private String codigoBarras;
    private Long idCategoria;
    private String nombreCategoria;
    private Long idProveedor;
    private String nombreProveedor;
    private String descripcion;
    private BigDecimal precioVenta;
    private BigDecimal precioCompra;
    private Integer stockActual;
    private Integer stockMinimo;
}
