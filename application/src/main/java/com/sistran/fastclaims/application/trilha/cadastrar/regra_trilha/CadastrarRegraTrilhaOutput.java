package com.sistran.fastclaims.application.trilha.cadastrar.regra_trilha;

import com.sistran.fastclaims.domain.trilha.RegraTrilhaPreview;

public record CadastrarRegraTrilhaOutput(
        String id,
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

    public static CadastrarRegraTrilhaOutput aPartirDe(final RegraTrilhaPreview regra) {
        return new CadastrarRegraTrilhaOutput(
                regra.regraId(),
                regra.nome(),
                regra.descricao(),
                regra.campoUm(),
                regra.operadorUm(),
                regra.campoDois(),
                regra.operadorDois(),
                regra.campoTres(),
                regra.resultadoEsperado(),
                regra.tipoAcao(),
                regra.ativa()
        );
    }
}


