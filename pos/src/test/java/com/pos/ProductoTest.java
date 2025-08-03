package com.pos;

import com.pos.dto.ProductoRequest;
import com.pos.dto.ProductoResponse;
import com.pos.mapper.ProductoMapper;
import com.pos.model.Categoria;
import com.pos.model.Producto;
import com.pos.model.Proveedor;
import com.pos.repository.CategoriaRepository;
import com.pos.repository.ProductoRepository;
import com.pos.repository.ProveedorRepository;
import com.pos.service.ProductoService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductoTest {

    @Mock ProductoRepository productoRepository;
    @Mock CategoriaRepository categoriaRepository;
    @Mock ProveedorRepository proveedorRepository;
    @Mock ProductoMapper productoMapper;

    @InjectMocks ProductoService productoService;

    @Test
    void debeCrearProductoCorrectamente() {
        // Arrange
        ProductoRequest dto = ProductoRequest.builder()
                .codigoBarras("123")
                .idCategoria(1L)
                .idProveedor(1L)
                .descripcion("Coca Cola")
                .precioVenta(new BigDecimal("10.00"))
                .precioCompra(new BigDecimal("6.00"))
                .stockActual(50)
                .stockMinimo(10)
                .build();

        Categoria categoria = new Categoria(); categoria.setId(1L);
        Proveedor proveedor = new Proveedor(); proveedor.setId(1L);

        Producto producto = new Producto();
        producto.setCodigoBarras("123");

        Producto productoGuardado = new Producto();
        productoGuardado.setId(100L);
        productoGuardado.setCodigoBarras("123");

        ProductoResponse expectedResponse = new ProductoResponse();
        expectedResponse.setId(100L);
        expectedResponse.setCodigoBarras("123");

        // Mocks
        when(productoRepository.existsByCodigoBarras("123")).thenReturn(false);
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedor));
        when(productoMapper.toEntity(dto)).thenReturn(producto);
        when(productoRepository.save(producto)).thenReturn(productoGuardado);
        when(productoMapper.toDto(productoGuardado)).thenReturn(expectedResponse);

        // Act
        ProductoResponse result = productoService.create(dto);

        // Assert
        assertEquals("123", result.getCodigoBarras());
        assertEquals(100L, result.getId());
    }

}
