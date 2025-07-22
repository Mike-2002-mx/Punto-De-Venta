package com.pos.mapper;

import org.springframework.stereotype.Component;

import com.pos.dto.ProveedorRequest;
import com.pos.dto.ProveedorResponse;
import com.pos.model.Proveedor;

@Component
public class ProveedorMapper {
    public Proveedor toEntity(ProveedorRequest dto) {
        return Proveedor.builder()
                .nombre(dto.getNombre())
                .build();
    }
    
    public ProveedorResponse toDto(Proveedor entity) {
        return ProveedorResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .build();
    }
}
