package com.sistran.fastclaims.infrastructure.colecao.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CadastrarColecaoRequest(

        @NotNull
        @NotBlank
        @Size(min = 3, max = 40)
        String nome,

        @NotNull
        @NotBlank
        @Size(min = 3, max = 40)
        String alias
) {
}
