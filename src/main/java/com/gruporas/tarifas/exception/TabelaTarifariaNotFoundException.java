package com.gruporas.tarifas.exception;

public class TabelaTarifariaNotFoundException extends RuntimeException {
    public TabelaTarifariaNotFoundException(Long id) {
        super("Tabela tarifaria de id " + id + " não foi encontrada.");
    }
}
