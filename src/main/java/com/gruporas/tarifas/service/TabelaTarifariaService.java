package com.gruporas.tarifas.service;

import com.gruporas.tarifas.dto.CategoriaDTO;
import com.gruporas.tarifas.dto.FaixaConsumoDTO;
import com.gruporas.tarifas.dto.TabelaTarifariaDTO;
import com.gruporas.tarifas.model.Categoria;
import com.gruporas.tarifas.model.FaixaConsumo;
import com.gruporas.tarifas.model.TabelaTarifaria;
import com.gruporas.tarifas.model.TabelaTarifariaCategoria;
import com.gruporas.tarifas.model.embeddable.TabelaTarifariaCategoriaId;
import com.gruporas.tarifas.repository.CategoriaRepository;
import com.gruporas.tarifas.repository.TabelaTarifariaRepository;
import com.gruporas.tarifas.utils.TabelaTarifariaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TabelaTarifariaService {

    @Autowired
    private TabelaTarifariaRepository tabelaTarifariaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public TabelaTarifaria createTabelaTarifaria(TabelaTarifariaDTO dto) {
        List<Categoria> categorias = categoriaRepository.findAll();
        TabelaTarifariaValidator.validate(dto, categorias);

        // revogando tabelas anteriores
        tabelaTarifariaRepository.revogarTabelasTarifarias();

        // criando a tabela tarifária
        TabelaTarifaria tabela = new TabelaTarifaria();
        tabela.setNome(dto.getNome());
        tabela.setVigente(true);
        tabela.setDataCriacao(LocalDateTime.now());

        for (CategoriaDTO categoriaDTO : dto.getCategorias()) {
            Categoria categoria = categorias.stream()
                    .filter(c -> c.getNome().equals(categoriaDTO.getNome()))
                    .findFirst()
                    .orElse(null);

            TabelaTarifariaCategoria tabelaCategoria = new TabelaTarifariaCategoria();
            tabelaCategoria.setId(new TabelaTarifariaCategoriaId());
            tabelaCategoria.setCategoria(categoria);
            tabelaCategoria.setTabelaTarifaria(tabela);

            // criando faixas
            for (FaixaConsumoDTO faixaDTO : categoriaDTO.getFaixas()) {
                FaixaConsumo faixa = new FaixaConsumo();
                faixa.setInicio(faixaDTO.getInicio());
                faixa.setFim(faixaDTO.getFim());
                faixa.setValor(faixaDTO.getValor());
                faixa.setTabelaTarifariaCategoria(tabelaCategoria);
                tabelaCategoria.getFaixasConsumo().add(faixa);
            }

            tabela.getTarifas().add(tabelaCategoria);
        }

        return tabelaTarifariaRepository.save(tabela);
    }

}
