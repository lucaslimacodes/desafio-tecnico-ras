package com.gruporas.tarifas.repository;

import com.gruporas.tarifas.model.TabelaTarifaria;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TabelaTarifariaRepository extends JpaRepository<TabelaTarifaria, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE TabelaTarifaria t SET t.vigente = false")
    void revogarTabelasTarifarias();
}
