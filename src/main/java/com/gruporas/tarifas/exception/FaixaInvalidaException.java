package com.gruporas.tarifas.exception;

public class FaixaInvalidaException extends RuntimeException {
    public FaixaInvalidaException(String message, String categoria) {
        super("Erro na categoria: " + categoria + "\n" + message);
    }
}
