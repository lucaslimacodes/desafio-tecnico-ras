package com.gruporas.tarifas.repository;

import com.gruporas.tarifas.model.FaixaConsumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaixaConsumoRepository extends JpaRepository<FaixaConsumo, Long> {

    @Query( "SELECT c " +
            "FROM FaixaConsumo c " +
            "WHERE c.tabelaTarifariaCategoria.categoria.nome = :nomeCategoria " +
            "AND c.tabelaTarifariaCategoria.tabelaTarifaria.vigente = true")
    List<FaixaConsumo> findAllByNomeCategoriaAndTabelaVigente(@Param("nomeCategoria") String nomeCategoria);

}
