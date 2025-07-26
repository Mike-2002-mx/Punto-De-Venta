package com.pos.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pos.dto.AjusteInventarioRequest;
import com.pos.dto.AjusteInventarioResponse;
import com.pos.dto.ProductoAjustadoRequest;
import com.pos.dto.ProductoAjustadoResponse;
import com.pos.exception.ResourceNotFoundException;
import com.pos.mapper.AjusteInventarioMapper;
import com.pos.mapper.ProductoAjustadoMapper;
import com.pos.model.AjusteInventario;
import com.pos.model.Producto;
import com.pos.model.ProductoAjustado;
import com.pos.repository.AjusteInventarioRepository;
import com.pos.repository.ProductoAjustadoRepository;
import com.pos.repository.ProductoRepository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AjusteInventarioService {
    
    private final AjusteInventarioRepository ajusteInventarioRepository;
    private final ProductoRepository productoRepository;
    private final ProductoAjustadoRepository productoAjustadoRepository;
    private final AjusteInventarioMapper ajusteInventarioMapper;
    private final ProductoAjustadoMapper productoAjustadoMapper;

    
    private static final Logger log = LoggerFactory.getLogger(AjusteInventarioService.class);

    @Transactional
    public AjusteInventarioResponse registrarAjuste(AjusteInventarioRequest request){
        
        //validaciones basicas 
        if(request == null){
            throw new IllegalArgumentException("La solicitud de Ajuste de inventario no puede ser nula");
        }

        if(request.getProductos()== null || request.getProductos().isEmpty()){
            throw new IllegalArgumentException("El ajuste debe tener al menos un producto");
        }
    

        BigDecimal totalNeg = BigDecimal.ZERO;
        BigDecimal totalPos = BigDecimal.ZERO;
        BigDecimal totalGen = BigDecimal.ZERO;
        Map<Long, ProductoAjustadoResponse> detallesProductos = new HashMap<>();

        for (ProductoAjustadoRequest productoReq : request.getProductos()) {
            if(productoReq.getNuevaExistencia() < 0){
                throw new IllegalArgumentException("La nueva existencia no puede ser menor que 0");
            }
             // Buscar producto
            Producto producto = productoRepository.findById(productoReq.getIdProducto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + productoReq.getIdProducto()));

            //Obtener diferencia y subtotal
            Integer diferencia = productoReq.getNuevaExistencia() - producto.getStockActual();
            BigDecimal subtotal = producto.getPrecioVenta().multiply(BigDecimal.valueOf(diferencia));
            
            //Agregar a totalNegativo o positivo 
            if (subtotal.signum() <= 0) {
                totalNeg = totalNeg.add(subtotal);
            } else {
                totalPos = totalPos.add(subtotal);
            }
            
            //Crear productoAjustadoResponse 
            ProductoAjustadoResponse detalleProducto = ProductoAjustadoResponse.
                builder()
                .descripcionProducto(producto.getDescripcion())
                .existenciaAnterior(producto.getStockActual())
                .nuevaExistencia(productoReq.getNuevaExistencia())
                .diferencia(diferencia)
                .precioUnitario(producto.getPrecioVenta())
                .subtotal(subtotal)
                .build();

            //Guardar valores ya calculados en detallesProductos
            detallesProductos.put(producto.getId(), detalleProducto);
            System.out.println("Detalles del producto: " + detalleProducto.toString());
        }

        //Calcular total general 
        System.out.println("Total Negativo: "+ totalNeg);
        System.out.println("Total Positivo: "+ totalPos);
        totalGen = totalPos.add(totalNeg);

        try {
            AjusteInventario ajusteInventario = ajusteInventarioMapper.toEntity(request);
            //Crear folio y agregar
            Integer lastFolio = (ajusteInventarioRepository.findLastFolioNumber()) + 1;
            String folio = String.format("%06d", lastFolio);
            ajusteInventario.setFolio(folio);
            //Asignar totales 
            ajusteInventario.setTotalPositivo(totalPos);
            ajusteInventario.setTotalNegativo(totalNeg);
            ajusteInventario.setTotalGeneral(totalGen);
            ajusteInventario = ajusteInventarioRepository.save(ajusteInventario);
            System.out.println("AJUSTE GUARDADO CON FOLIO: " + ajusteInventario.getFolio());

            //Guardar detalles de los producto
            for (ProductoAjustadoRequest productoReq : request.getProductos()) {
                 // Buscar producto
                Producto producto = productoRepository.findById(productoReq.getIdProducto())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + productoReq.getIdProducto()));
                
                //Actualizar stock del producto
                producto.setStockActual(productoReq.getNuevaExistencia());
                productoRepository.save(producto);
                
                //Traer valores de productos
                ProductoAjustadoResponse productoAjustadoResponse = detallesProductos.get(producto.getId());

                ProductoAjustado productoAjustado = productoAjustadoMapper.toEntity(productoReq);
                productoAjustado.setProducto(producto);
                productoAjustado.setAjusteInventario(ajusteInventario);
                productoAjustado.setExistenciaAnterior(productoAjustadoResponse.getExistenciaAnterior());
                productoAjustado.setDiferencia(productoAjustadoResponse.getDiferencia());
                productoAjustado.setPrecioUnitario(productoAjustadoResponse.getPrecioUnitario());
                productoAjustado.setSubtotal(productoAjustadoResponse.getSubtotal());
                productoAjustado = productoAjustadoRepository.save(productoAjustado);
                System.out.println("PRODUCTO AJUSTADO PARA PRODUCTO CON id: " + productoAjustado.getProducto().getId());                
            }

            //Preparar respuesta
            AjusteInventarioResponse ajusteInventarioResponse = ajusteInventarioMapper.toDto(ajusteInventario);
            //Asiganar productos ajustados
            ajusteInventarioResponse.setProductosVendidos(detallesProductos.values()
                   .stream()
                   .collect(Collectors.toList()));

            return ajusteInventarioResponse;
        } catch (Exception e) {
            System.out.println(e + "Error al registra el ajuste de inventario");
            throw e; // 
        }

    }   

}
