package com.gruporas.tarifas.infra;

import com.gruporas.tarifas.exception.TabelaTarifariaNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;


@RestControllerAdvice
public class TarifasExceptionHandler {

    @ExceptionHandler(TabelaTarifariaNotFoundException.class)
    public ResponseEntity<ErroDTO> handleTabelaNotFoundException(TabelaTarifariaNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErroDTO.builder().status(HttpStatus.NOT_FOUND).mensagem(exception.getMessage()).build());
    }

    // Para FaixaInvalidaException e CategoriaInvalidaException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErroDTO> handleDefaultException(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErroDTO.builder().status(HttpStatus.BAD_REQUEST).mensagem(exception.getMessage()).build());
    }

    // Para erro na validação de DTOs
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroDTO> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErroDTO.builder().status(HttpStatus.BAD_REQUEST).mensagem(mensagem).build());
    }

}
