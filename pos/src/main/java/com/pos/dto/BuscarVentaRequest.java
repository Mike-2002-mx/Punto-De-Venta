package com.pos.dto;

import lombok.Data;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuscarVentaRequest {
    
    @NotBlank(message = "La fecha de inicio es obligatoria")
    private LocalDateTime fechaInicio;

    @NotBlank(message = "La fecha de inicio es obligatoria")
    private LocalDateTime fechaFin;

    private Long idProducto;
}
