package com.sistran.fastclaims.domain.trilha;


public record RegraTrilhaPreview(
        String regraId,
        String nome,
        String descricao,
        String campoUm,
        String operadorUm,
        String campoDois,
        String operadorDois,
        String campoTres,
        boolean resultadoEsperado,
        String tipoAcao,
        boolean ativa
) {

    public static RegraTrilhaPreview aPartirDe(
            final String regraId,
            final String nome,
            final String descricao,
            final String campoUm,
            final String operadorUm,
            final String campoDois,
            final String operadorDois,
            final String campoTres,
            final boolean resultadoEsperado,
            final String tipoAcao,
            final boolean ativa
    ) {
        return new RegraTrilhaPreview(regraId, nome, descricao, campoUm, operadorUm, campoDois, operadorDois, campoTres, resultadoEsperado, tipoAcao, ativa);
    }
}

