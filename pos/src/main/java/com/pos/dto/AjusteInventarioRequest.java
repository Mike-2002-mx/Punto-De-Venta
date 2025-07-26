package com.pos.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AjusteInventarioRequest {

    @NotBlank(message = "El motivo no puede estar vacío")
    @Size(max = 100, message = "El motivo no puede exceder los 100 caracteres")
    private String motivo;

    @NotEmpty(message = "Debe haber al menos un producto en la venta.")
    @Valid
    private List<AjusteInventarioRequest> productos;

}
