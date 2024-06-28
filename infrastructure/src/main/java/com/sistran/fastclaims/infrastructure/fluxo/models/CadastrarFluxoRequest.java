package com.sistran.fastclaims.infrastructure.fluxo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastrarFluxoRequest(
        @NotBlank
        @Size(min = 2, max = 50)
        String descricao,
        @NotBlank
        String naturezaId
) {
}
