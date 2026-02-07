package com.gruporas.tarifas.dto;

import com.gruporas.tarifas.model.Categoria;
import com.gruporas.tarifas.model.FaixaConsumo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {

    @NotEmpty(message = "Nome não pode ser vazio")
    private String nome;

    @NotNull(message = "a lista de faixas não pode ser nula")
    @Valid
    private List<FaixaConsumoDTO> faixas;

    public CategoriaDTO(Categoria categoria, List<FaixaConsumo> faixas) {
        this.nome = categoria.getNome();
        this.faixas = faixas.stream().map(FaixaConsumoDTO::new).toList();
    }
}
