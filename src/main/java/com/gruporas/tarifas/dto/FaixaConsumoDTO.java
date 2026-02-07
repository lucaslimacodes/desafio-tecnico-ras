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

    @NotNull(message = "A faixa precisa ter um valor de inicio")
    @Min(value = 0, message = "O valor de inicio da faixa deve ser maior ou igual a 0")
    private Integer inicio;

    @NotNull(message = "A faixa deve ter um valor de fim")
    private Integer fim;

    @NotNull(message = "A faixa deve ter um valor a ser cobrado")
    @Min(value = 0, message = "O valor cobrado deve ser ao menos 0")
    private Float valor;

    public FaixaConsumoDTO(FaixaConsumo faixaConsumo) {
        this.inicio = faixaConsumo.getInicio();
        this.fim = faixaConsumo.getFim();
        this.valor = faixaConsumo.getValor();
    }
}
