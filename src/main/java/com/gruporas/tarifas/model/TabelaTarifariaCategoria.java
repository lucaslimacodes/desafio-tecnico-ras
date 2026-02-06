package com.gruporas.tarifas.model;

import com.gruporas.tarifas.model.embeddable.TabelaTarifariaCategoriaId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TABELA_TARIFARIA_CATEGORIA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TabelaTarifariaCategoria {

    @EmbeddedId
    private TabelaTarifariaCategoriaId id;

    @ManyToOne
    @MapsId("idTabelaTarifaria")
    @JoinColumn(name = "id_tabela_tarifaria")
    private TabelaTarifaria tabelaTarifaria;

    @ManyToOne
    @MapsId("idCategoria")
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @OneToMany(mappedBy = "tabelaTarifariaCategoria",  cascade = CascadeType.ALL)
    private List<FaixaConsumo> faixasConsumo = new ArrayList<>();
}
