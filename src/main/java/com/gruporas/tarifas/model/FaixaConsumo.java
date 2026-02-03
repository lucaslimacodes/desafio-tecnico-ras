package com.gruporas.tarifas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "FAIXA_CONSUMO")
public class FaixaConsumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_tabela_tarifaria", insertable = false, updatable = false),
            @JoinColumn(name = "id_categoria", insertable = false, updatable = false)
    })
    private TabelaTarifariaCategoria tabelaTarifariaCategoria;

    @Column(nullable = false)
    private Integer inicio;

    @Column(nullable = false)
    private Integer fim;

    @Column(nullable = false)
    private Float valor;
}
