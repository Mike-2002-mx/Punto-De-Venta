package com.pos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pos.dto.ProductoRequest;
import com.pos.dto.ProductoResponse;
import com.pos.exception.ResourceAlreadyExistsException;
import com.pos.exception.ResourceNotFoundException;
import com.pos.mapper.ProductoMapper;
import com.pos.model.Categoria;
import com.pos.model.Producto;
import com.pos.model.Proveedor;
import com.pos.repository.CategoriaRepository;
import com.pos.repository.ProductoRepository;
import com.pos.repository.ProveedorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {
    
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProveedorRepository proveedorRepository; 
    private final ProductoMapper productoMapper; 
    
    @Transactional(readOnly = true)
    public List<ProductoResponse> findAll(){
        return productoRepository.findAll().
            stream().map(productoMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductoResponse findById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        return productoMapper.toDto(producto);
    }

    @Transactional(readOnly = true)
    public List<ProductoResponse> findProductsByText(String text){
        List <ProductoResponse> productos = productoRepository.searchByText(text).
                        stream().map(productoMapper::toDto)
                        .collect(Collectors.toList());
        return productos;
    }

    @Transactional(readOnly = true)
    public ProductoResponse findProductsByCode(String code){
        Producto producto = productoRepository.findByCodigoBarras(code)
                            .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con codigo: " + code));
        return productoMapper.toDto(producto);
    }

    @Transactional
    public ProductoResponse create(ProductoRequest productoDTO) {
        if (productoRepository.existsByCodigoBarras(productoDTO.getCodigoBarras().toLowerCase())) {
            throw new ResourceAlreadyExistsException("Ya existe un producto con el código de barras: " + productoDTO.getCodigoBarras());
        }
        
        Categoria categoria = categoriaRepository.findById(productoDTO.getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + productoDTO.getIdCategoria()));
        
        Proveedor proveedor = proveedorRepository.findById(productoDTO.getIdProveedor())
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con id: " + productoDTO.getIdProveedor()));
        
        Producto producto = productoMapper.toEntity(productoDTO);
        producto.setCategoria(categoria);
        producto.setProveedor(proveedor);
        
        producto = productoRepository.save(producto);
        return productoMapper.toDto(producto);
    }

    @Transactional
    public ProductoResponse update(Long id, ProductoRequest productoDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        
        if (productoRepository.existsByCodigoBarrasAndIdNot(productoDTO.getCodigoBarras(), id)) {
            throw new ResourceAlreadyExistsException("Ya existe otro producto con el código de barras: " + productoDTO.getCodigoBarras());
        }
        
        Categoria categoria = categoriaRepository.findById(productoDTO.getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + productoDTO.getIdCategoria()));
        
        Proveedor proveedor = proveedorRepository.findById(productoDTO.getIdProveedor())
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con id: " + productoDTO.getIdProveedor()));
        
        producto.setCodigoBarras(productoDTO.getCodigoBarras());
        producto.setCategoria(categoria);
        producto.setProveedor(proveedor);
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecioVenta(productoDTO.getPrecioVenta());
        producto.setPrecioCompra(productoDTO.getPrecioCompra());
        producto.setStockActual(productoDTO.getStockActual());
        producto.setStockMinimo(productoDTO.getStockMinimo());
        
        producto = productoRepository.save(producto);
        return productoMapper.toDto(producto);
    }

    @Transactional
    public void delete(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con id: " + id);
        }
        productoRepository.deleteById(id);
    }


}
