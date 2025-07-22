package com.pos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pos.dto.ProveedorRequest;
import com.pos.dto.ProveedorResponse;
import com.pos.exception.ResourceAlreadyExistsException;
import com.pos.exception.ResourceNotFoundException;
import com.pos.mapper.ProveedorMapper;
import com.pos.model.Proveedor;
import com.pos.repository.ProveedorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProveedorService {
    
    private final ProveedorRepository proveedorRepository;
    private final ProveedorMapper proveedorMapper;

    @Transactional(readOnly = true)
    public List<ProveedorResponse> findAll() {
        return proveedorRepository.findAll()
                .stream()
                .map(proveedorMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public ProveedorResponse findById(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con id: " + id));
        return proveedorMapper.toDto(proveedor);
    }
    
    @Transactional
    public ProveedorResponse create(ProveedorRequest proveedorDTO) {
        if (proveedorRepository.existsByNombre(proveedorDTO.getNombre())) {
            throw new ResourceAlreadyExistsException("Ya existe un proveedor con el nombre: " + proveedorDTO.getNombre());
        }
        
        Proveedor proveedor = proveedorMapper.toEntity(proveedorDTO);
        proveedor = proveedorRepository.save(proveedor);
        return proveedorMapper.toDto(proveedor);
    }
    
    @Transactional
    public ProveedorResponse update(Long id, ProveedorRequest proveedorDTO) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con id: " + id));
        
        if (proveedorRepository.existsByNombreAndIdNot(proveedorDTO.getNombre(), id)) {
            throw new ResourceAlreadyExistsException("Ya existe otro proveedor con el nombre: " + proveedorDTO.getNombre());
        }
        
        proveedor.setNombre(proveedorDTO.getNombre());
        proveedor = proveedorRepository.save(proveedor);
        return proveedorMapper.toDto(proveedor);
    }
    
    @Transactional
    public void delete(Long id) {
        if (!proveedorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Proveedor no encontrado con id: " + id);
        }
        proveedorRepository.deleteById(id);
    }

}
