package com.gruporas.tarifas.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TabelaTarifariaCategoriaId implements Serializable {

    @Column(name = "id_tabela_tarifaria")
    private Long idTabelaTarifaria;

    @Column(name = "id_categoria")
    private Long idCategoria;

}
