package com.gruporas.tarifas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculoResponseDTO {
    private String categoria;
    private Integer consumoTotal;
    private Float valorTotal;
    private List<CobrancaFaixaDTO> detalhamento;
}
