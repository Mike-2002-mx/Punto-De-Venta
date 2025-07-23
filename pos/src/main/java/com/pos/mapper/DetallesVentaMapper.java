package com.pos.mapper;

import org.springframework.stereotype.Component;

import com.pos.dto.DetallesVentaRequest;
import com.pos.dto.DetallesVentaResponse;
import com.pos.model.DetallesVenta;

@Component
public class DetallesVentaMapper {
    
    public DetallesVenta toEntity(DetallesVentaRequest request){
        return DetallesVenta.builder()
                .cantidad(request.getCantidad())
                .build();
    }

    public DetallesVentaResponse toDto(DetallesVenta entity){
        return DetallesVentaResponse.builder()
                    .descripcionProducto(entity.getProducto().getDescripcion())
                    .cantidad(entity.getCantidad())
                    .precioVenta(entity.getPrecioVenta())
                    .subtotal(entity.getSubtotal())
                    .build();
    }
}
