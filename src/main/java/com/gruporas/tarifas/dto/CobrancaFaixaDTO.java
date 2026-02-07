package com.gruporas.tarifas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CobrancaFaixaDTO {
    private FaixaInicioFimDTO faixa;
    private Integer m3Cobrados;
    private Float valorUnitario;
    private Float subtotal;
}
