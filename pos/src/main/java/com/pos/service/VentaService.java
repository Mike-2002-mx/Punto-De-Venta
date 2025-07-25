package com.pos.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pos.dto.BuscarVentaRequest;
import com.pos.dto.DetallesVentaRequest;
import com.pos.dto.DetallesVentaResponse;
import com.pos.dto.VentaRequest;
import com.pos.dto.VentaResponse;
import com.pos.exception.ResourceNotFoundException;
import com.pos.exception.StockInsufficientException;
import com.pos.exception.TotalSaleNotValidException;
import com.pos.mapper.DetallesVentaMapper;
import com.pos.mapper.VentaMapper;
import com.pos.model.DetallesVenta;
import com.pos.model.Producto;
import com.pos.model.Venta;
import com.pos.repository.DetallesVentaRepository;
import com.pos.repository.ProductoRepository;
import com.pos.repository.VentaRepository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class VentaService {
    
    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final DetallesVentaRepository detallesVentaRepository;
    private final DetallesVentaMapper detallesVentaMapper;
    private final VentaMapper ventaMapper;

    private static final Logger log = LoggerFactory.getLogger(VentaService.class);

    public Page<VentaResponse> findAll(Pageable pageable){
        //Obtener todas las ventas
        Page<Venta> ventasPage = ventaRepository.findAll(pageable);

        List<VentaResponse> ventas = mapearVentasConDetalles(ventasPage);

        //  Devolver la página con resultados
        return new PageImpl<>(ventas, pageable, ventasPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Page<VentaResponse> findByFechaAndProducto(BuscarVentaRequest request, Pageable pageable){

        if (request.getFechaInicio().isAfter(request.getFechaFin())) {
            throw new IllegalArgumentException("La fecha limite no puede ser antes que la fecha de inicio");
        } 

        Page<Venta> rangoVentas;

        //Obtener todas las ventas por fecha o por fecha y producto
        if(request.getIdProducto() == null){
            rangoVentas = ventaRepository.findVentasPorFecha(request.getFechaInicio() , request.getFechaFin(), pageable);
        } else{
            rangoVentas = ventaRepository.findVentasPorFechaAndIdProducto(request.getFechaInicio() , request.getFechaFin(), request.getIdProducto(), pageable);
        }

        List<VentaResponse> ventas = mapearVentasConDetalles(rangoVentas);

        return new PageImpl<>(ventas, pageable, rangoVentas.getTotalElements());
    }

    public VentaResponse findByFolio(String folio){
        Venta venta = ventaRepository.findByFolio(folio).orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con folio: " + folio));
        List<DetallesVentaResponse> detallesVenta = detallesVentaRepository.findByIdVenta(venta.getId())
            .stream()
            .map(detallesVentaMapper::toDto)
            .collect(Collectors.toList());
        VentaResponse ventaResponse = ventaMapper.toDto(venta);
        ventaResponse.setProductosVendidos(detallesVenta);
        return ventaResponse;
    }

    private List<VentaResponse> mapearVentasConDetalles(Page<Venta> rangoVentas) {
        // Convertir ventas a una lista de VentaResponse
        List<VentaResponse> ventas = rangoVentas
            .getContent()
            .stream()
            .map(ventaMapper::toDto)
            .collect(Collectors.toList());

        // Obtener lista de IDs
        List<Long> ventaIds = ventas
            .stream()
            .map(VentaResponse::getId)
            .collect(Collectors.toList());

        // Consultar detalles por ID de venta
        List<DetallesVenta> detalles = detallesVentaRepository.findByVenta_IdIn(ventaIds);

        // Agrupar detalles por ID de venta y mapearlos a DTOs
        Map<Long, List<DetallesVentaResponse>> detallesPorVenta = detalles
            .stream()
            .collect(Collectors.groupingBy(
                d -> d.getVenta().getId(),
                Collectors.mapping(detallesVentaMapper::toDto, Collectors.toList())
            ));

        // Asignar productos vendidos a cada venta
        for (VentaResponse venta : ventas) {
            venta.setProductosVendidos(detallesPorVenta.getOrDefault(venta.getId(), List.of()));
        }
        return ventas;
    }


    @Transactional
    public VentaResponse registerVenta(VentaRequest request){
        //Validaciones básicas
        if (request == null) {
            throw new IllegalArgumentException("La solicitud de venta no puede ser nula");
        }
        
        if (request.getProductos() == null || request.getProductos().isEmpty()) {
            throw new IllegalArgumentException("La venta debe tener al menos un producto");
        }

        //Validar que el pago sea mayor o igual al total
        if(request.getPagoCon().compareTo(request.getTotal()) < 0){
            throw new TotalSaleNotValidException("El pago no es suficiente");
        }
        
        // Validar que el total sea positivo
        if (request.getTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new TotalSaleNotValidException("El total de la venta debe ser mayor que cero");
        }

        // 4. CALCULAR Y VALIDAR CONSISTENCIA DEL TOTAL
        BigDecimal totalCalculado = BigDecimal.ZERO;
        
        // Primera pasada: validar productos y calcular total
        for (DetallesVentaRequest detalleReq : request.getProductos()) {
            // Validar detalle
            if (detalleReq.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
            }

            // Buscar producto
            Producto producto = productoRepository.findById(detalleReq.getIdProducto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + detalleReq.getIdProducto()));

            //Obtener de la base de datos el precio unitario del procucto
            BigDecimal precioUnitario = producto.getPrecioVenta();

            // Validar stock ANTES de cualquier modificación
            if (producto.getStockActual() < detalleReq.getCantidad()) {
                throw new StockInsufficientException("Stock insuficiente para el producto: " + producto.getDescripcion() 
                    + ". Stock disponible: " + producto.getStockActual() + ", solicitado: " + detalleReq.getCantidad());
            }

            // Calcular subtotal
            BigDecimal subtotal = precioUnitario.multiply(new BigDecimal(detalleReq.getCantidad()));
            totalCalculado = totalCalculado.add(subtotal);
            
        }

        //Detalles válidos
        List<DetallesVentaRequest> detallesValidados = request.getProductos();

        // 5. VALIDAR CONSISTENCIA DEL TOTAL
        if (totalCalculado.compareTo(request.getTotal()) != 0) {
            throw new TotalSaleNotValidException("El total enviado (" + request.getTotal() 
                + ") no coincide con el total calculado (" + totalCalculado + ")");
        }

        try {
            // 6. Guardar venta en la base de datos
            Venta venta = ventaMapper.toEntity(request);
            //Generar folio
            Integer lastFolio = (ventaRepository.findLastFolioNumber()) + 1;
            String folio = String.format("%06d", lastFolio);
            venta.setFolio(folio);

            //Calcular cambio 
            venta.setCambio(request.getPagoCon().subtract(totalCalculado));

            venta = ventaRepository.save(venta);
            log.info("Venta guardada con ID: {} y folio: {}", venta.getId(), venta.getFolio());

            // 7. Segunda pasada: actualizar stock y crear detalles
            List<DetallesVenta> detallesGuardados = new ArrayList<>();
            
            for (DetallesVentaRequest validado : detallesValidados) {
                // Actualizar stock del producto
                Producto producto = productoRepository.findById(validado.getIdProducto())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + validado.getIdProducto()));
                producto.setStockActual(producto.getStockActual() - validado.getCantidad());
                productoRepository.save(producto);

                // Crear y guardar detalle de venta
                DetallesVenta detalle = detallesVentaMapper.toEntity(validado);
                BigDecimal precioVentaUnitario = producto.getPrecioVenta();
                detalle.setPrecioVenta(precioVentaUnitario);
                BigDecimal subtotalVenta = precioVentaUnitario.multiply(new BigDecimal(validado.getCantidad()));
                detalle.setSubtotal(subtotalVenta);
                detalle.setProducto(producto);
                detalle.setVenta(venta); 

                DetallesVenta detalleGuardado = detallesVentaRepository.save(detalle);
                detallesGuardados.add(detalleGuardado);
                
                log.debug("Detalle guardado para producto ID: {}, cantidad: {}, subtotal: {}", 
                    producto.getId(), detalle.getCantidad(), detalle.getSubtotal());
            }

            // 8. Preparar respuesta
            VentaResponse ventaResponse = ventaMapper.toDto(venta);
            // Si el mapper no incluye los detalles, agregarlos manualmente
            ventaResponse.setProductosVendidos(detallesGuardados.stream().map(detallesVentaMapper::toDto).collect(Collectors.toList()));
            
            log.info("Venta registrada exitosamente con ID: {} y total: {}", venta.getId(), venta.getTotal());
            return ventaResponse;
        
        } catch (Exception e) {
            log.error("Error al registrar venta con total: {}", request.getTotal(), e);
            throw e; // Re-lanzar para que @Transactional haga rollback
        }
    }
}