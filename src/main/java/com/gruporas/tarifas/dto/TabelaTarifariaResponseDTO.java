package com.gruporas.tarifas.dto;

import com.gruporas.tarifas.model.TabelaTarifaria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TabelaTarifariaResponseDTO {
    private Long id;
    private String nome;
    private LocalDateTime dataCriacao;
    private Boolean vigente;
    private List<CategoriaDTO> categorias;

    public TabelaTarifariaResponseDTO(TabelaTarifaria tabelaTarifaria) {
        this.id = tabelaTarifaria.getId();
        this.nome = tabelaTarifaria.getNome();
        this.dataCriacao = tabelaTarifaria.getDataCriacao();
        this.vigente = tabelaTarifaria.getVigente();
        this.categorias = tabelaTarifaria.getTarifas().stream()
                .map(tarifa -> new CategoriaDTO(tarifa.getCategoria(), tarifa.getFaixasConsumo())).toList();
    }
}
