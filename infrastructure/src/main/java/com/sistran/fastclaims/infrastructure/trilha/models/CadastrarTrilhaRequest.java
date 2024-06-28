package com.sistran.fastclaims.infrastructure.trilha.models;

import jakarta.validation.constraints.NotBlank;

public record CadastrarTrilhaRequest(
        @NotBlank
        String nome,
        String descricao,
        String fluxoId
) {
}
