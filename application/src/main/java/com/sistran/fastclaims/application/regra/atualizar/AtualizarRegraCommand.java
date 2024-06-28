package com.sistran.fastclaims.application.regra.atualizar;

public record AtualizarRegraCommand(
        String id,
        String nome,
        String descricao,
        String campoUm,
        String operadorUm,
        String campoDois,
        String operadorDois,
        String campoTres
) {

    public static AtualizarRegraCommand aPartirDe(
            final String id,
            final String nome,
            final String descricao,
            final String campoUm,
            final String operadorUm,
            final String campoDois,
            final String operadorDois,
            final String campoTres
    ) {
        return new AtualizarRegraCommand(id, nome, descricao, campoUm, operadorUm, campoDois, operadorDois, campoTres);
    }

}


