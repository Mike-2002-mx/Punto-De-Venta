package com.pos.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoRequest {
    
    @NotBlank(message = "El código de barras no puede estar vacío")
    @Size(max = 20, message = "El código de barras no puede exceder los 20 caracteres")
    private String codigoBarras;

    @NotNull(message = "El ID de categoría es obligatorio")
    private Long idCategoria;

    @NotNull(message = "El ID de proveedor es obligatorio")
    private Long idProveedor;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String descripcion;

    @NotNull(message = "El precio de venta es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio de venta debe ser mayor que 0")
    private BigDecimal precioVenta;

    @NotNull(message = "El precio de compra es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio de compra debe ser mayor que 0")
    private BigDecimal precioCompra;

    @NotNull(message = "El stock actual es obligatorio")
    @Min(value = 0, message = "El stock actual no puede ser negativo")
    private Integer stockActual;

    @NotNull(message = "El stock mínimo es obligatorio")
    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    private Integer stockMinimo;
}
