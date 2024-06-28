package com.sistran.fastclaims.application.regra.cadastrar;

public record CadastrarRegraCommand(
        String nome,
        String descricao,
        String campoUm,
        String operadorUm,
        String campoDois,
        String operadorDois,
        String campoTres
) {

    public static CadastrarRegraCommand aPartirDe(
            final String nome,
            final String descricao,
            final String campoUm,
            final String operadorUm,
            final String campoDois,
            final String operadorDois,
            final String campoTres
    ) {
        return new CadastrarRegraCommand(nome, descricao, campoUm, operadorUm, campoDois, operadorDois, campoTres);
    }
}
