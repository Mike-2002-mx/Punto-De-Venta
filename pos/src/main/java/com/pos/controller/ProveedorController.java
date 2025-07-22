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

import com.pos.dto.ProveedorRequest;
import com.pos.dto.ProveedorResponse;
import com.pos.service.ProveedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/proveedores")
@RequiredArgsConstructor
@Tag(name = "Proveedores", description = "API para el manejo de proveedores")
public class ProveedorController {
    private final ProveedorService proveedorService;
    
    @Operation(
        summary = "Obtener todos los proveedores",
        description = "Retorna una lista de todos los proveedores existentes"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de proveedores encontrada",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ProveedorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<ProveedorResponse>> findAll() {
        List<ProveedorResponse> proveedores = proveedorService.findAll();
        return ResponseEntity.ok(proveedores);
    }
    

    @Operation(
        summary = "Obtener un proveedor por ID",
        description = "Retorna un proveedor específico basado en su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Proveedor encontrado",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ProveedorResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Proveedor no encontrado",
            content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponse> findById(@PathVariable Long id) {
        ProveedorResponse proveedor = proveedorService.findById(id);
        return ResponseEntity.ok(proveedor);
    }
    
    @Operation(
        summary = "Agregar un nuevo proveedor",
        description = "Agrega un nuevo proveedor y retorna el proveedor creado"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Proveedor agregado exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ProveedorResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content),
        @ApiResponse(
            responseCode = "409",
            description = "Ya existe un proveedor con ese nombre",
            content = @Content)
    })
    @PostMapping
    public ResponseEntity<ProveedorResponse> create(@Valid @RequestBody ProveedorRequest proveedorDTO) {
        ProveedorResponse nuevoProveedor = proveedorService.create(proveedorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProveedor);
    }
    
    @Operation(
        summary = "Actualizar un proveedor existente",
        description = "Actualiza los datos de un proveedor existente y retorna el proveedor actualizado"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Proveedor actualizado exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ProveedorResponse.class))),
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
    public ResponseEntity<ProveedorResponse> update(
            @PathVariable Long id, 
            @Valid @RequestBody ProveedorRequest proveedorDTO) {
        ProveedorResponse proveedorActualizado = proveedorService.update(id, proveedorDTO);
        return ResponseEntity.ok(proveedorActualizado);
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
        proveedorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
