package com.sistran.fastclaims.infrastructure.trilha.models;

public record CadastrarRegraTrilhaResponse(
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
        boolean ativa,
        String trilhaId
) {

    public static CadastrarRegraTrilhaResponse aPartirDe(
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
            boolean ativa,
            String trilhaId
    ) {
        return new CadastrarRegraTrilhaResponse(
                regraId,
                nome,
                descricao,
                campoUm,
                operadorUm,
                campoDois,
                operadorDois,
                campoTres,
                resultadoEsperado,
                tipoAcao,
                ativa,
                trilhaId
        );
    }
}
