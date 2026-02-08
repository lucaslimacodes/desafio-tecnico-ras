package com.gruporas.tarifas.infra;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErroDTO {
    private HttpStatus status;
    private String mensagem;
}
