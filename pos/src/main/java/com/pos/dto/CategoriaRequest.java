package com.pos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaRequest {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres")
    private String nombre;
}
