package com.pos.mapper;

import org.springframework.stereotype.Component;

import com.pos.dto.VentaRequest;
import com.pos.dto.VentaResponse;
import com.pos.model.Venta;

@Component
public class VentaMapper {
    
    public Venta toEntity(VentaRequest request){
        return Venta.builder()
                .folio(request.getFolio())
                .total(request.getTotal())
                .pagoCon(request.getPagoCon())
                .cambio(request.getCambio())
                .build();
    }

    public VentaResponse toDto(Venta entity){
        return VentaResponse.builder()
                .id(entity.getId())
                .folio(entity.getFolio())
                .fecha(entity.getFecha())
                .total(entity.getTotal())
                .pagoCon(entity.getPagoCon())
                .cambio(entity.getCambio())
                .build();
    }

}
