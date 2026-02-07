package com.gruporas.tarifas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculoRequestDTO {

    @NotEmpty(message = "categoria não pode ser nulo ou em branco")
    private String categoria;

    @Min(value = 1, message = "Consumo deve ser ao menos 1")
    private Integer consumo;
}
