package com.gruporas.tarifas.dto;

import com.gruporas.tarifas.model.FaixaConsumo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaixaInicioFimDTO {
    private Integer inicio;
    private Integer fim;

    public FaixaInicioFimDTO(FaixaConsumo faixaConsumo) {
        this.inicio = faixaConsumo.getInicio();
        this.fim = faixaConsumo.getFim();
    }
}
