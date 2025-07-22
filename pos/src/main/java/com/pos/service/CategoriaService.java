package com.pos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pos.dto.CategoriaRequest;
import com.pos.dto.CategoriaResponse;
import com.pos.exception.ResourceAlreadyExistsException;
import com.pos.exception.ResourceNotFoundException;
import com.pos.mapper.CategoriaMapper;
import com.pos.model.Categoria;
import com.pos.repository.CategoriaRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    
    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Transactional(readOnly = true)
    public List<CategoriaResponse> findAll() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoriaResponse findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));
        return categoriaMapper.toDto(categoria);
    }

    @Transactional
    public CategoriaResponse create(CategoriaRequest categoriaDTO) {
        if (categoriaRepository.existsByNombre(categoriaDTO.getNombre())) {
            throw new ResourceAlreadyExistsException("Ya existe una categoría con el nombre: " + categoriaDTO.getNombre());
        }
        
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        categoria = categoriaRepository.save(categoria);
        return categoriaMapper.toDto(categoria);
    }

    @Transactional
    public CategoriaResponse update(Long id, CategoriaRequest categoriaDTO) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));
        
        if (categoriaRepository.existsByNombreAndIdNot(categoriaDTO.getNombre(), id)) {
            throw new ResourceAlreadyExistsException("Ya existe otra categoría con el nombre: " + categoriaDTO.getNombre());
        }
        
        categoria.setNombre(categoriaDTO.getNombre());
        categoria = categoriaRepository.save(categoria);
        return categoriaMapper.toDto(categoria);
    }

    @Transactional
    public void delete(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoría no encontrada con id: " + id);
        }
        categoriaRepository.deleteById(id);
    }
}
