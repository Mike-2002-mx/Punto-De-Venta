package com.pos.exception;

public class StockInsufficientException extends RuntimeException{
    public StockInsufficientException(String mensaje) {
        super(mensaje);
    }
}
