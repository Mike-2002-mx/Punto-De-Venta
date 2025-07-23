package com.pos.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallesVentaRequest {
    
    //EL ID de la venta se genera durante la venta
    // @NotNull(message = "El ID de la venta es obligatoria")
    // private Long idVenta;

    @NotNull(message = "El ID del prodcuto es obligatorio")
    private Long idProducto;

    @NotNull(message = "La cantidad es obligatoria.")
    @Min(value = 1, message = "La cantidad debe ser al menos 1.")
    private Integer cantidad;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio de venta del producto debe ser mayor que 0")
    private BigDecimal precioVenta;

    @NotNull(message = "El subtotal es obligatorio")
    @DecimalMin(value = "0.01", message = "El subtotal de la venta debe ser mayor que 0")
    private BigDecimal subtotal;
}
