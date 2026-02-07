package com.gruporas.tarifas.controller;

import com.gruporas.tarifas.dto.CalculoRequestDTO;
import com.gruporas.tarifas.dto.CalculoResponseDTO;
import com.gruporas.tarifas.service.CalculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calculos")
@Tag(
        name = "Cálculos",
        description = "Endpoint para realizar o cálculo do consumo"
)
public class CalculoController {

    @Autowired
    private CalculoService calculoService;

    @Operation(description = "Realiza cálculo de consumo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "tabela tarifaria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Categoria inválida"),
            @ApiResponse(responseCode = "404", description = "Ainda não existe tabela tarifária no sistema.")
    })
    @PostMapping
    private ResponseEntity<CalculoResponseDTO> calculateTaxa(@RequestBody @Valid CalculoRequestDTO dto){
        return ResponseEntity.ok(calculoService.calcularConsumo(dto));
    }
}
