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

    // //Se puede hacer en el backend
    // @NotBlank(message = "El folio no puede estar vacío")
    // @Size(max = 20, message = "El folio no puede exceder los 20 caracteres")
    // private String folio;

    @NotNull(message = "El total de venta es obligatorio")
    @DecimalMin(value = "0.01", message = "El total de venta debe ser mayor que 0")
    private BigDecimal total;

    @NotNull(message = "El pago con de la venta es obligatorio")
    @DecimalMin(value = "0.01", message = "El pago con de la venta debe ser mayor que 0")
    private BigDecimal pagoCon;

    // //Se puede calcular en el backend
    // @NotNull(message = "El cambio de la venta es obligatorio")
    // @DecimalMin(value = "0.0", inclusive = true, message = "El cambio no puede ser negativo")
    // private BigDecimal cambio;

    @NotEmpty(message = "Debe haber al menos un producto en la venta.")
    @Valid
    private List<DetallesVentaRequest> productos;
}
