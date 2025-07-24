package com.pos.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ventas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Venta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El folio no puede estar vacío")
    @Size(max = 20, message = "El folio no puede exceder los 20 caracteres")
    @Column(name = "folio", unique = true, length = 20)
    private String folio;

    @CreationTimestamp
    @Column(name = "fecha", updatable = false)
    private LocalDateTime fecha;

    @NotNull(message = "El total de venta es obligatorio")
    @DecimalMin(value = "0.01", message = "El total de venta debe ser mayor que 0")
    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @NotNull(message = "El pago con de la venta es obligatorio")
    @DecimalMin(value = "0.01", message = "El pago con de la venta debe ser mayor que 0")
    @Column(name = "pago_con", nullable = false, precision = 10, scale = 2)
    private BigDecimal pagoCon;

    @NotNull(message = "El cambio de la venta es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El cambio no puede ser negativo")
    @Column(name = "cambio", nullable = false, precision = 10, scale = 2)
    private BigDecimal cambio;

}
