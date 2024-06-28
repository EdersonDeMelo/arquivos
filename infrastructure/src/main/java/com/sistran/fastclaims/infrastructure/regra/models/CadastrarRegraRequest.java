package com.sistran.fastclaims.infrastructure.regra.models;

import jakarta.validation.constraints.NotBlank;

public record CadastrarRegraRequest(
        @NotBlank
        String nome,
        String descricao,
        @NotBlank
        String campoUm,
        String operadorUm,
        String campoDois,
        @NotBlank
        String operadorDois,
        @NotBlank
        String campoTres
) {
}
