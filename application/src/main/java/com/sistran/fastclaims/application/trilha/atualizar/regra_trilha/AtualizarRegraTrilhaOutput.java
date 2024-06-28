package com.sistran.fastclaims.application.trilha.atualizar.regra_trilha;

import com.sistran.fastclaims.domain.trilha.RegraTrilha;

public record AtualizarRegraTrilhaOutput(
        String regraId,
        boolean resultadoEsperado,
        String tipoAcao,
        boolean ativa
) {
    public static AtualizarRegraTrilhaOutput aPartirDe(final RegraTrilha regraTrilha) {
        return new AtualizarRegraTrilhaOutput(
                regraTrilha.id().getValue(),
                regraTrilha.resultadoEsperado(),
                regraTrilha.tipoAcao().getNome(),
                regraTrilha.ativa()
        );
    }
}


