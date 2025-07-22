package com.pos.mapper;

import org.springframework.stereotype.Component;

import com.pos.dto.CategoriaRequest;
import com.pos.dto.CategoriaResponse;
import com.pos.model.Categoria;

@Component
public class CategoriaMapper {
    
    public Categoria toEntity(CategoriaRequest dto) {
        return Categoria.builder()
                .nombre(dto.getNombre())
                .build();
    }
    
    public CategoriaResponse toDto(Categoria entity) {
        return CategoriaResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .build();
    }

}
