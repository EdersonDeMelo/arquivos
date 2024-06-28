package com.sistran.fastclaims.infrastructure.trilha.models;

import jakarta.validation.constraints.NotBlank;

public record AtualizarRegraTrilhaRequest(
        boolean resultadoEsperado,
        @NotBlank
        String tipoAcao,
        boolean ativa
) {
}
