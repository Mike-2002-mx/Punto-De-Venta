package com.pos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pos.dto.ProductoRequest;
import com.pos.dto.ProductoResponse;
import com.pos.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "API para el manejo de productos")
public class ProductoController {

    private final ProductoService productoService;
    
    @Operation(
        summary = "Obtener todos los productos",
        description = "Retorna una lista de todos los productos existentes"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de productos encontrada",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ProductoResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<ProductoResponse>> findAll() {
        List<ProductoResponse> productos = productoService.findAll();
        return ResponseEntity.ok(productos);
    }

    @Operation(
        summary = "Obtener todos los productos por palabra clave o código de barras",
        description = "Retorna una lista de todos los productos existentes"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de productos encontrada",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ProductoResponse.class)))
    })
    @GetMapping("search/{text}")
    public ResponseEntity<List<ProductoResponse>> findProductosByText(String text) {
        List<ProductoResponse> productos = productoService.findProductsByText(text);
        return ResponseEntity.ok(productos);
    }
    
    @Operation(
        summary = "Obtener un producto por ID",
        description = "Retorna uun producto específico basada en su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Producto encontrado",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ProductoResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado",
            content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> findById(@PathVariable Long id) {
        ProductoResponse producto = productoService.findById(id);
        return ResponseEntity.ok(producto);
    }
    
    @Operation(
        summary = "Crear un nuevo producto",
        description = "Crea un nuevo producto y retorna el producto creado"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Producto creado exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ProductoResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content),
        @ApiResponse(
            responseCode = "409",
            description = "Ya existe un producto con ese nombre",
            content = @Content)
    })
    @PostMapping
    public ResponseEntity<ProductoResponse> create(@Valid @RequestBody ProductoRequest productoDTO) {
        ProductoResponse nuevoProducto = productoService.create(productoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }
    
    @Operation(
        summary = "Actualizar un producto existente",
        description = "Actualiza los datos de un producto existente y retorna el producto actualizado"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Producto actualizado exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ProductoResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrada",
            content = @Content),
        @ApiResponse(
            responseCode = "409",
            description = "Ya existe otro producto con ese nombre",
            content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> update(
            @PathVariable Long id, 
            @Valid @RequestBody ProductoRequest productoDTO) {
        ProductoResponse productoActualizado = productoService.update(id, productoDTO);
        return ResponseEntity.ok(productoActualizado);
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
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
