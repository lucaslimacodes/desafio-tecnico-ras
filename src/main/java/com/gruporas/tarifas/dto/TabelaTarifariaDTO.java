package com.gruporas.tarifas.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TabelaTarifariaDTO {

    @NotEmpty(message = "O nome da tabela não pode ser nula")
    private String nome;

    @NotNull(message = "A tabela precisa ter categorias")
    @Size(min = 1, message = "Deve haver ao menos uma categoria")
    @Valid
    private List<CategoriaDTO> categorias;
}
