package com.pos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class Testear {
    
    @Test
    void crearProductoCorrectamente(){
        //Given --Teniendo
        String nombre = "Coca cola";
        double precio = 12.5;
        
        //When --Cuando
        Product product = new Product(nombre, precio);
        
        //Then --Entonces
        assertEquals("Coca cola", product.getNombre());
        assertEquals(12.5, product.getPrecio());
    }

    @Test
    void lanzarExcepcionSiNombreEsVacio(){
        //Given
        String nombre = "";
        double precio = 12.5;

        //Then
        assertThrows(IllegalArgumentException.class, 
            () -> {Product product = new Product(nombre, precio);}
        );
    }

    @Test
    void lanzarExcepcionSiPrecioEsMenorQueCero(){
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {Product product = new Product("Coca", -12);}
        );

        assertEquals("El precio no puede ser negativo", ex.getMessage());
        
    }

    @Test
    void siAplicaDescuento(){
        //Given 
        Product product = new Product("Coca", 12);
        double porcentaje = 50;
        //WHen 
        product.aplicarDescuento(porcentaje);
        //Then
        assertEquals(6, product.getPrecio());
    }

    @Test
    void descuentoInvalido(){
        //Given 
        Product product = new Product("Coca", 12);
        double porcentaje = -10;
        //WHen 
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {product.aplicarDescuento(porcentaje);});
        //Then
        assertEquals("Porcentaje de descuento inválido", ex.getMessage());
    }

}
