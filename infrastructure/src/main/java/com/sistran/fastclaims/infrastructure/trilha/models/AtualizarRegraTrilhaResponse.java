package com.sistran.fastclaims.infrastructure.trilha.models;

public record AtualizarRegraTrilhaResponse(
        String regraId,
        String trilhaId,
        boolean resultadoEsperado,
        String tipoAcao,
        boolean ativa
) {

    public static AtualizarRegraTrilhaResponse aPartirDe(
            String regraId,
            String trilhaId,
            boolean resultadoEsperado,
            String tipoAcao,
            boolean ativa
    ) {
        return new AtualizarRegraTrilhaResponse(
                regraId,
                trilhaId,
                resultadoEsperado,
                tipoAcao,
                ativa
        );
    }
}
