package com.gruporas.tarifas.service;

import com.gruporas.tarifas.dto.CategoriaDTO;
import com.gruporas.tarifas.dto.FaixaConsumoDTO;
import com.gruporas.tarifas.dto.TabelaTarifariaDTO;
import com.gruporas.tarifas.dto.TabelaTarifariaResponseDTO;
import com.gruporas.tarifas.exception.CategoriaInvalidaException;
import com.gruporas.tarifas.exception.TabelaTarifariaNotFoundException;
import com.gruporas.tarifas.model.Categoria;
import com.gruporas.tarifas.model.FaixaConsumo;
import com.gruporas.tarifas.model.TabelaTarifaria;
import com.gruporas.tarifas.model.TabelaTarifariaCategoria;
import com.gruporas.tarifas.model.embeddable.TabelaTarifariaCategoriaId;
import com.gruporas.tarifas.repository.CategoriaRepository;
import com.gruporas.tarifas.repository.FaixaConsumoRepository;
import com.gruporas.tarifas.repository.TabelaTarifariaRepository;
import com.gruporas.tarifas.utils.TabelaTarifariaValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TabelaTarifariaService {

    @Autowired
    private TabelaTarifariaRepository tabelaTarifariaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private FaixaConsumoRepository faixaConsumoRepository;

    public List<TabelaTarifaria> getAllTabelaTarifaria() {
        return tabelaTarifariaRepository.findAll();
    }

    @Transactional
    public void deleteTabelaTarifariaById(Long id) {

        TabelaTarifaria tabelaTarifaria = tabelaTarifariaRepository.findById(id)
                .orElseThrow(() -> new TabelaTarifariaNotFoundException(id));

        tabelaTarifariaRepository.delete(tabelaTarifaria);
        tabelaTarifariaRepository.flush();

        // se deletou a tabela vigente
        if(tabelaTarifaria.getVigente()){
            // a tabela mais recente volta a ser a vigente
            Optional<TabelaTarifaria> tabelaMaisRecente = tabelaTarifariaRepository.findFirstByOrderByDataCriacaoDesc();
            tabelaMaisRecente.ifPresent(tabela -> tabela.setVigente(true));
        }
    }

    public TabelaTarifaria createTabelaTarifaria(TabelaTarifariaDTO dto) {
        List<Categoria> categorias = categoriaRepository.findAll();
        TabelaTarifariaValidator.validate(dto, categorias);

        // revogando tabelas anteriores
        tabelaTarifariaRepository.revogarTabelasTarifarias();

        // criando a tabela tarifária
        TabelaTarifaria tabela = new TabelaTarifaria();
        tabela.setNome(dto.getNome());
        tabela.setVigente(true);
        tabela.setDataCriacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

        for (CategoriaDTO categoriaDTO : dto.getCategorias()) {
            Categoria categoria = categorias.stream()
                    .filter(c -> c.getNome().equals(categoriaDTO.getNome().toUpperCase()))
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

    public List<FaixaConsumo> getFaixasByCategoria(String categoria) {
        List<FaixaConsumo> faixas = faixaConsumoRepository.findAllByNomeCategoriaAndTabelaVigente(categoria.toUpperCase());
        if(faixas.isEmpty()){
            throw new CategoriaInvalidaException("Categoria inválida ou ainda não existe tabela tarifária no banco");
        }
        return faixas;
    }

}
