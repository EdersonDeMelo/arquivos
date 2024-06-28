package com.sistran.fastclaims.infrastructure.colecaocampo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CadastrarColecaoCampoRequest(
        @NotNull
        @NotBlank
        @Size(min = 3, max = 30)
        String campo,

        @NotNull
        @NotBlank
        @Size(min = 3, max = 30)
        String alias,

        @NotNull
        @NotBlank
        String tipoDado,

        @NotNull
        @NotBlank
        String tipoFormato,

        @NotNull
        @NotBlank
        String colecaoId,

        String dominioId) {
}
