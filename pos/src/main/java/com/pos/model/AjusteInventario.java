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
@Table(name = "ajustesinventario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class AjusteInventario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "fecha", updatable = false)
    private LocalDateTime fecha;

    @NotBlank(message = "El folio no puede estar vacío")
    @Size(max = 20, message = "El folio no puede exceder los 20 caracteres")
    @Column(name = "folio", unique = true, length = 20)
    private String folio;

    @NotNull(message = "El motivo es obligatorio")
    @Column(name = "motivo", nullable = false, length = 100)
    private String motivo;

    @NotNull(message = "El total negativo es obligatorio")
    @Column(name = "total_negativo", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalNegativo;

    @NotNull(message = "El total positivo es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El total positivo no puede ser negativo")
    @Column(name = "total_positivo", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPositivo;

    @NotNull(message = "El total positivo es obligatorio")
    @Column(name = "total_general", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalGeneral;

}

