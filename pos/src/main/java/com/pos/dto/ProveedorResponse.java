package com.pos.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProveedorResponse {
    private Long id;
    private String nombre;
}