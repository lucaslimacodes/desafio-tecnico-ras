package com.gruporas.tarifas.dto;

import com.gruporas.tarifas.model.FaixaConsumo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FaixaConsumoDTO {

    @NotNull
    @Min(0)
    private Integer inicio;

    @NotNull
    private Integer fim;

    @NotNull
    @Min(0)
    private Float valor;

    public FaixaConsumoDTO(FaixaConsumo faixaConsumo) {
        this.inicio = faixaConsumo.getInicio();
        this.fim = faixaConsumo.getFim();
        this.valor = faixaConsumo.getValor();
    }
}
