package com.sistran.fastclaims.infrastructure.trilha.models;

import jakarta.validation.constraints.NotBlank;

public record AtualizarTrilhaRequest(
        @NotBlank
        String nome,
        String descricao
) {
}
