package com.sistran.fastclaims.application.trilha.atualizar.regra_trilha;

public record AtualizarRegraTrilhaCommand(
        String regraId,
        String trilhaId,
        boolean resultadoEsperado,
        String tipoAcao,
        boolean ativa
) {

    public static AtualizarRegraTrilhaCommand aPartirDe(
            String regraId,
            String trilhaId,
            boolean resultadoEsperado,
            String tipoAcao,
            boolean ativa
    ) {
        return new AtualizarRegraTrilhaCommand(
                regraId,
                trilhaId,
                resultadoEsperado,
                tipoAcao,
                ativa
        );
    }
}
