package com.pos.mapper;

import org.springframework.stereotype.Component;

import com.pos.dto.ProductoRequest;
import com.pos.dto.ProductoResponse;
import com.pos.model.Producto;

@Component
public class ProductoMapper {
    public Producto toEntity(ProductoRequest dto) {
        return Producto.builder()
                .codigoBarras(dto.getCodigoBarras())
                .descripcion(dto.getDescripcion())
                .precioVenta(dto.getPrecioVenta())
                .precioCompra(dto.getPrecioCompra())
                .stockActual(dto.getStockActual())
                .stockMinimo(dto.getStockMinimo())
                .build();
    }
    
    public ProductoResponse toDto(Producto entity) {
        return ProductoResponse.builder()
                .id(entity.getId())
                .codigoBarras(entity.getCodigoBarras())
                .idCategoria(entity.getCategoria().getId())
                .nombreCategoria(entity.getCategoria().getNombre())
                .idProveedor(entity.getProveedor().getId())
                .nombreProveedor(entity.getProveedor().getNombre())
                .descripcion(entity.getDescripcion())
                .precioVenta(entity.getPrecioVenta())
                .precioCompra(entity.getPrecioCompra())
                .stockActual(entity.getStockActual())
                .stockMinimo(entity.getStockMinimo())
                .build();
    }
}
