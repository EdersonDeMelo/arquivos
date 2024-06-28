package com.sistran.fastclaims.infrastructure.trilha.models;

public record CadastrarRegraTrilhaRequest(
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
}
