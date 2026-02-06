package com.gruporas.tarifas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TABELA_TARIFARIA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TabelaTarifaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Column
    private Boolean vigente;

    @OneToMany(mappedBy = "tabelaTarifaria", cascade = CascadeType.ALL)
    private List<TabelaTarifariaCategoria> tarifas = new ArrayList<>();
}
