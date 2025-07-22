package com.pos.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El código de barras no puede estar vacío")
    @Size(max = 20, message = "El código de barras no puede exceder los 20 caracteres")
    @Column(name = "codigo_barras", unique = true, nullable = false, length = 20)
    private String codigoBarras;

    @NotNull(message = "La categoría es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @NotNull(message = "El proveedor es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor")
    private Proveedor proveedor;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    @Column(nullable = false, length = 255)
    private String descripcion;

    @NotNull(message = "El precio de venta es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio de venta debe ser mayor que 0")
    @Column(name = "precio_venta", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioVenta;

    @NotNull(message = "El precio de compra es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio de compra debe ser mayor que 0")
    @Column(name = "precio_compra", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioCompra;

    @NotNull(message = "El stock actual es obligatorio")
    @Min(value = 0, message = "El stock actual no puede ser negativo")
    @Column(name = "stock_actual", nullable = false)
    private Integer stockActual;

    @NotNull(message = "El stock mínimo es obligatorio")
    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    @Column(name = "stock_minimo", nullable = false)
    private Integer stockMinimo;
}
