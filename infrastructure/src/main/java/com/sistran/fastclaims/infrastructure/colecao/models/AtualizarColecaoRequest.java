package com.sistran.fastclaims.infrastructure.colecao.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AtualizarColecaoRequest(
        @NotNull
        @NotBlank
        @Size(min = 3, max = 40)
        String alias
) {
}
