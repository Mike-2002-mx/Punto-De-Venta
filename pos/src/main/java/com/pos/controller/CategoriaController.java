package com.pos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pos.dto.CategoriaRequest;
import com.pos.dto.CategoriaResponse;
import com.pos.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/categorias")
@RequiredArgsConstructor
@Tag(name = "Categorías", description = "API para el manejo de categorías")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Operation(
        summary = "Obtener todas las categorías",
        description = "Retorna una lista de todas las categorías existentes"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de categorías encontrada",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = CategoriaResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> findAll() {
        List<CategoriaResponse> categorias = categoriaService.findAll();
        return ResponseEntity.ok(categorias);
    }


    @Operation(
        summary = "Obtener una categoría por ID",
        description = "Retorna una categoría específica basada en su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Categoría encontrada",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = CategoriaResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Categoría no encontrada",
            content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> findById(@PathVariable Long id) {
        CategoriaResponse categoria = categoriaService.findById(id);
        return ResponseEntity.ok(categoria);
    }
    
    @Operation(
        summary = "Crear una nueva categoría",
        description = "Crea una nueva categoría y retorna la categoría creada"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Categoría creada exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = CategoriaResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content),
        @ApiResponse(
            responseCode = "409",
            description = "Ya existe una categoría con ese nombre",
            content = @Content)
    })
    @PostMapping
    public ResponseEntity<CategoriaResponse> create(@Valid @RequestBody CategoriaRequest categoriaDTO) {
        CategoriaResponse nuevaCategoria = categoriaService.create(categoriaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria);
    }

    @Operation(
        summary = "Actualizar una categoría existente",
        description = "Actualiza los datos de una categoría existente y retorna la categoría actualizada"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Categoría actualizada exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = CategoriaResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "Categoría no encontrada",
            content = @Content),
        @ApiResponse(
            responseCode = "409",
            description = "Ya existe otra categoría con ese nombre",
            content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> update(
            @PathVariable Long id, 
            @Valid @RequestBody CategoriaRequest categoriaDTO) {
        CategoriaResponse categoriaActualizada = categoriaService.update(id, categoriaDTO);
        return ResponseEntity.ok(categoriaActualizada);
    }
    
    @Operation(
        summary = "Eliminar una categoría",
        description = "Elimina una categoría existente basada en su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Categoría eliminada exitosamente",
            content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "Categoría no encontrada",
            content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
