package com.pos.mapper;

import org.springframework.stereotype.Component;

import com.pos.dto.AjusteInventarioRequest;
import com.pos.dto.AjusteInventarioResponse;
import com.pos.model.AjusteInventario;

@Component
public class AjusteInventarioMapper {
    
    public AjusteInventario toEntity(AjusteInventarioRequest request){
        return AjusteInventario.builder()
                .motivo(request.getMotivo())
                .build();
    }

    public AjusteInventarioResponse toDto(AjusteInventario entity){
        return AjusteInventarioResponse.builder()
                .id(entity.getId())
                .folio(entity.getFolio())
                .motivo(entity.getMotivo())
                .fecha(entity.getFecha())
                .totalNegativo(entity.getTotalNegativo())
                .totalPositivo(entity.getTotalPositivo())
                .totalGeneral(entity.getTotalGeneral())
                .build();
    }

}
