package com.sistran.fastclaims.application.trilha.cadastrar.regra_trilha;


public record CadastrarRegraTrilhaCommand(
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

    public static CadastrarRegraTrilhaCommand aPartirDe(
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
            final boolean ativa,
            final String trilhaId
    ) {
        return new CadastrarRegraTrilhaCommand(regraId, nome, descricao, campoUm, operadorUm, campoDois, operadorDois, campoTres, resultadoEsperado, tipoAcao, ativa, trilhaId);
    }
}


