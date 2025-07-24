package com.pos.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pos.dto.VentaRequest;
import com.pos.dto.VentaResponse;
import com.pos.service.VentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/ventas")
@RequiredArgsConstructor
@Tag(name = "Ventas", description = "API para el manejo de ventas")
public class VentaController {
    
    private final VentaService ventaService;

    @Operation(
        summary = "Obtener todas las ventas",
        description = "Retorna una lista de todas las ventas existentes"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Ventas encontradas",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = VentaResponse.class)))
    })
    @GetMapping
    public ResponseEntity<Page<VentaResponse>> getAll(Pageable pageable) {
        Page<VentaResponse> ventas = ventaService.findAll(pageable);
        return ResponseEntity.ok(ventas);
    }

    @Operation(
        summary = "Registar una nueva venta",
        description = "Registra una nueva venta y retorna la venta creada"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Venta registrada exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = VentaResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content),
        @ApiResponse(
            responseCode = "409",
            description = "Ya existe una venta con ese nombre",
            content = @Content)
    })
    @PostMapping
    public ResponseEntity<VentaResponse> create(@Valid @RequestBody VentaRequest ventaRequest) {
        System.out.println("Venta: " +ventaRequest.toString());
        VentaResponse nuevaVenta = ventaService.registerVenta(ventaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
    }

}
