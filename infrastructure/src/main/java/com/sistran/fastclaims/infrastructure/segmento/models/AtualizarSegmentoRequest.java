package com.sistran.fastclaims.infrastructure.segmento.models;

import jakarta.validation.constraints.NotBlank;

public record AtualizarSegmentoRequest(
        @NotBlank
        String nome
) {
}
