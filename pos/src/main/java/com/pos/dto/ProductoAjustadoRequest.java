package com.pos.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoAjustadoRequest {
    
    @NotNull(message = "El ID del prodcuto es obligatorio")
    private Long idProducto;

    @NotNull(message = "La nueva existencia es obligatoria.")
    @Min(value = 0, message = "La nueva existencia no puede ser menor que 0")
    private Integer nuevaExistencia;
}
