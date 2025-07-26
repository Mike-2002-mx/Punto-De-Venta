package com.pos.mapper;

import org.springframework.stereotype.Component;

import com.pos.dto.ProductoAjustadoRequest;
import com.pos.dto.ProductoAjustadoResponse;
import com.pos.model.ProductoAjustado;

@Component
public class ProductoAjustadoMapper {
    
    public ProductoAjustado toEntity(ProductoAjustadoRequest request){
        return ProductoAjustado.builder()
                .id(request.getIdProducto())
                .nuevaExistencia(request.getNuevaExistencia())
                .build();
    }

    public ProductoAjustadoResponse toDto(ProductoAjustado entity){
        return ProductoAjustadoResponse.builder()
                .descripcionProducto(entity.getProducto().getDescripcion())
                .existenciaAnterior(entity.getExistenciaAnterior())
                .nuevaExistencia(entity.getNuevaExistencia())
                .diferencia(entity.getDiferencia())
                .precioUnitario(entity.getPrecioUnitario())
                .subtotal(entity.getSubtotal())
                .build();
    }

}
