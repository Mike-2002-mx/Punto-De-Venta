package com.pos.exception;

public class TotalSaleNotValidException extends RuntimeException{
    public TotalSaleNotValidException(String mensaje) {
        super(mensaje);
    }
}
