package com.gruporas.tarifas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FAIXA_CONSUMO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FaixaConsumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_tabela_tarifaria", referencedColumnName = "id_tabela_tarifaria"),
            @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")
    })
    private TabelaTarifariaCategoria tabelaTarifariaCategoria;

    @Column(nullable = false)
    private Integer inicio;

    @Column(nullable = false)
    private Integer fim;

    @Column(nullable = false)
    private Float valor;
}
