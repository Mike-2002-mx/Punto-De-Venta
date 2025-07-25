package com.pos.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaRequest {


    @NotNull(message = "El total de venta es obligatorio")
    @DecimalMin(value = "0.01", message = "El total de venta debe ser mayor que 0")
    private BigDecimal total;

    @NotNull(message = "El pago con de la venta es obligatorio")
    @DecimalMin(value = "0.01", message = "El pago con de la venta debe ser mayor que 0")
    private BigDecimal pagoCon;

    @NotEmpty(message = "Debe haber al menos un producto en la venta.")
    @Valid
    private List<DetallesVentaRequest> productos;
}
