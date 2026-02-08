package com.gruporas.tarifas.controller;

import com.gruporas.tarifas.dto.TabelaTarifariaDTO;
import com.gruporas.tarifas.dto.TabelaTarifariaResponseDTO;
import com.gruporas.tarifas.infra.ErroDTO;
import com.gruporas.tarifas.model.TabelaTarifaria;
import com.gruporas.tarifas.service.TabelaTarifariaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tabelas-tarifarias")
@Tag(
        name = "Tabela Tarifaria",
        description = "Endpoints relacionados às tabelas tarifarias"
)
public class TabelaTarifariaController {

    @Autowired
    private TabelaTarifariaService tabelaTarifariaService;

    @Operation(description = "Cria uma tabela tarifaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "tabela tarifaria criada com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro de Validação",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErroDTO.class)
                    )
            )
    })
    @PostMapping
    private ResponseEntity<TabelaTarifariaResponseDTO> createTabelaTarifaria(@Valid @RequestBody TabelaTarifariaDTO dto) {
        TabelaTarifariaResponseDTO response =  new TabelaTarifariaResponseDTO(tabelaTarifariaService.createTabelaTarifaria(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(description = "retorna todas as tabelas tarifárias cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "lista de tabelas retornada"),
    })
    @GetMapping
    private ResponseEntity<List<TabelaTarifariaResponseDTO>> getAllTabelaTarifaria() {
        List<TabelaTarifaria> tabelas = tabelaTarifariaService.getAllTabelaTarifaria();
        return ResponseEntity.status(HttpStatus.OK).body(tabelas.stream().map(TabelaTarifariaResponseDTO::new).toList());
    }

    @Operation(description = "Deleta uma tabela tarifária por id.\nSe for a tabela vigente, deixa a tabela mais recente como vigente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tabela deletada com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tabela não encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErroDTO.class))
            )
    })
    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteTabelaTarifaria(@PathVariable Long id) {
        tabelaTarifariaService.deleteTabelaTarifariaById(id);
        return ResponseEntity.noContent().build();
    }
}
