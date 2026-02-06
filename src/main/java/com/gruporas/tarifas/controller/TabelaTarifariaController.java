package com.gruporas.tarifas.controller;

import com.gruporas.tarifas.dto.TabelaTarifariaDTO;
import com.gruporas.tarifas.dto.TabelaTarifariaResponseDTO;
import com.gruporas.tarifas.service.TabelaTarifariaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @ApiResponse(responseCode = "400", description = "Erro de Validação")
    })
    @PostMapping
    private ResponseEntity<TabelaTarifariaResponseDTO> createTabelaTarifaria(@Valid @RequestBody TabelaTarifariaDTO dto) {
        TabelaTarifariaResponseDTO response =  new TabelaTarifariaResponseDTO(tabelaTarifariaService.createTabelaTarifaria(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
