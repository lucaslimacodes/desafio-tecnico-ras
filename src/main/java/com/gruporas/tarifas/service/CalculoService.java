package com.gruporas.tarifas.service;

import com.gruporas.tarifas.dto.CalculoRequestDTO;
import com.gruporas.tarifas.dto.CalculoResponseDTO;
import com.gruporas.tarifas.dto.CobrancaFaixaDTO;
import com.gruporas.tarifas.dto.FaixaInicioFimDTO;
import com.gruporas.tarifas.exception.CategoriaInvalidaException;
import com.gruporas.tarifas.exception.TabelaTarifariaNotFoundException;
import com.gruporas.tarifas.model.Categoria;
import com.gruporas.tarifas.model.FaixaConsumo;
import com.gruporas.tarifas.repository.CategoriaRepository;
import com.gruporas.tarifas.repository.FaixaConsumoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalculoService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private FaixaConsumoRepository faixaConsumoRepository;

    public CalculoResponseDTO calcularConsumo(CalculoRequestDTO dto){
        if(!categoriaRepository.existsByNome(dto.getCategoria().toUpperCase())){
            throw new CategoriaInvalidaException("A categoria " + dto.getCategoria() + " não existe.");
        }

        // obtendo as faixas da categoria em questão a partir da tabela vigente
        List<FaixaConsumo> faixas = faixaConsumoRepository.findAllByNomeCategoriaAndTabelaVigente(dto.getCategoria().toUpperCase());
        if(faixas.isEmpty()){
            throw new TabelaTarifariaNotFoundException("Não existe tabela tarifária cadastrada no sistema.");
        }

        CalculoResponseDTO calculoResponseDTO = new CalculoResponseDTO();
        calculoResponseDTO.setCategoria(dto.getCategoria().toUpperCase());
        calculoResponseDTO.setConsumoTotal(dto.getConsumo());
        calculoResponseDTO.setDetalhamento(new ArrayList<>());

        Integer consumoRestante = dto.getConsumo();
        Float valorTotal = 0f;

        // algoritmo do consumo
        for(FaixaConsumo faixaConsumo : faixas){
            if(consumoRestante == 0) break; // não há mais consumo para ser computado

            Integer deltaFaixa = faixaConsumo.getFim() - faixaConsumo.getInicio();

            FaixaInicioFimDTO faixaInicioFimDTO = new FaixaInicioFimDTO(faixaConsumo);
            CobrancaFaixaDTO cobrancaFaixaDTO = new CobrancaFaixaDTO();
            cobrancaFaixaDTO.setFaixa(faixaInicioFimDTO);
            cobrancaFaixaDTO.setValorUnitario(faixaConsumo.getValor());
            if(consumoRestante > deltaFaixa){
                cobrancaFaixaDTO.setM3Cobrados(deltaFaixa);
                cobrancaFaixaDTO.setSubtotal(faixaConsumo.getValor()*deltaFaixa);
                valorTotal += faixaConsumo.getValor()*deltaFaixa;
                consumoRestante -= deltaFaixa;
            }
            else{
                cobrancaFaixaDTO.setM3Cobrados(consumoRestante);
                cobrancaFaixaDTO.setSubtotal(faixaConsumo.getValor()*consumoRestante);
                valorTotal += faixaConsumo.getValor()*consumoRestante;
                consumoRestante = 0;
            }

            calculoResponseDTO.getDetalhamento().add(cobrancaFaixaDTO);
        }
        calculoResponseDTO.setValorTotal(valorTotal);
        return calculoResponseDTO;
    }


}
