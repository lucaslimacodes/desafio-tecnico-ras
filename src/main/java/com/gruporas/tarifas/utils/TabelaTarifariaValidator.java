package com.gruporas.tarifas.utils;

import com.gruporas.tarifas.dto.CategoriaDTO;
import com.gruporas.tarifas.dto.FaixaConsumoDTO;
import com.gruporas.tarifas.dto.TabelaTarifariaDTO;
import com.gruporas.tarifas.exception.CategoriaInvalidaException;
import com.gruporas.tarifas.exception.FaixaInvalidaException;
import com.gruporas.tarifas.model.Categoria;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TabelaTarifariaValidator {

    /**
     * Valida Tabela Tarifaria
     * @param dto dto proveniente da request
     * @param categorias categorias vindas do banco
     * @throws CategoriaInvalidaException caso alguma categoria não exista no banco
     * @throws FaixaInvalidaException caso alguma faixa seja inválida
     */
    public static void validate(TabelaTarifariaDTO dto, List<Categoria> categorias) throws CategoriaInvalidaException, FaixaInvalidaException {
        validateCategoria(dto, categorias);
        dto.getCategorias().forEach(TabelaTarifariaValidator::validateFaixa);
    }

    /**
     * Verifica se todas as categorias foram preenchidas e se não há repetição
     *
     * @param dto dto proveniente da request
     * @param categorias categorias vindas do banco
     * @throws CategoriaInvalidaException caso alguma categoria não exista no banco
     */
    private static void validateCategoria(TabelaTarifariaDTO dto, List<Categoria> categorias) throws CategoriaInvalidaException {
        List<Categoria> categoriaAux = new ArrayList<>(categorias);
        for(CategoriaDTO categoriaDTO : dto.getCategorias()) {
            Categoria categoria = categoriaAux.stream()
                    .filter(cat -> cat.getNome().equals(categoriaDTO.getNome().toUpperCase())).findFirst().orElse(null);
            if(categoria == null) {
                throw new CategoriaInvalidaException("A categoria " + categoriaDTO.getNome() + " não existe ou está duplicada");
            }
            categoriaAux.remove(categoria);
        }
        if(!categoriaAux.isEmpty()) {
            String[] categoriasRestantes = categoriaAux.stream().map(Categoria::getNome).toArray(String[]::new);
            throw new CategoriaInvalidaException("Essas categorias não foram preenchidas: " + String.join(" ", categoriasRestantes));
        }
    }

    /**
     * Verifica se as faixas são válidas
     * @param dto CategoriaDTO proveniente da request
     * @throws FaixaInvalidaException caso alguma faixa não seja válida
     */
    private static void validateFaixa(CategoriaDTO dto) throws FaixaInvalidaException {

        List<FaixaConsumoDTO> faixas = dto.getFaixas();

        // ordenar as faixas para melhor análise
        faixas.sort(Comparator.comparingInt(FaixaConsumoDTO::getInicio));

        // checar se final > inicial para todas as faixas
        for(FaixaConsumoDTO faixa : faixas) {
            if(faixa.getFim() <= faixa.getInicio()){
                throw new FaixaInvalidaException("O valor final de todas as faixas deve ser maior que o valor inicial", dto.getNome());
            }
        }

        // checar os extremos
        if(faixas.getFirst().getInicio() != 0){
            throw new FaixaInvalidaException("A primeira faixa deve ter valor inicial 0", dto.getNome());
        }

        if(faixas.getLast().getFim() < 99999){
            throw new FaixaInvalidaException("A última faixa deve ter valor final acima de 99999", dto.getNome());
        }

        // checar continuidade entre as faixas
        for(int i=0; i<faixas.size() - 1; i++){
            if(faixas.get(i).getFim() != (faixas.get(i+1).getInicio() - 1)){
                throw new FaixaInvalidaException("Há buracos ou interseções entre faixas", dto.getNome());
            }
        }

    }

}
