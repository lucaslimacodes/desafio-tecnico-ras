package com.gruporas.tarifas.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TabelaTarifariaDTO {

    @NotEmpty
    private String nome;

    @NotNull
    @Valid
    private List<CategoriaDTO> categorias;
}
