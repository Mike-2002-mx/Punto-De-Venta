package com.pos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pos.dto.AjusteInventarioRequest;
import com.pos.dto.AjusteInventarioResponse;
import com.pos.service.AjusteInventarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/ajustes-inventario")
@RequiredArgsConstructor
@Tag(name = "Ajustes Inventario", description = "API para el manejo de ajustes de inventario")
public class AjusteInventarioController {
    
    private final AjusteInventarioService ajusteInventarioService;

        @Operation(
        summary = "Registar un nuevo ajuste de inventario",
        description = "Registra un nuevo ajuste de inventario y retorna el ajuste creado"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Ajuste registrado exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = AjusteInventarioResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content)
    })
    @PostMapping
    public ResponseEntity<AjusteInventarioResponse> create(@Valid @RequestBody AjusteInventarioRequest ajusteRequest) {
        System.out.println("Venta: " +ajusteRequest.toString());
        AjusteInventarioResponse nuevoAjuste = ajusteInventarioService.registrarAjuste(ajusteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAjuste);
    }

}
