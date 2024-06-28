package com.sistran.fastclaims.application.trilha.excluir.regra_trilha;

public record ExcluirRegraTrilhaCommand(
        String trilhaId,
        String regraId
) {
}
