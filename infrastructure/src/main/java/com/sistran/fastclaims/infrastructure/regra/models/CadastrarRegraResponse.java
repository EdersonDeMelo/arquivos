package com.sistran.fastclaims.infrastructure.regra.models;

public record CadastrarRegraResponse(
        String id,
        String nome,
        String descricao,
        String campoUm,
        String operadorUm,
        String campoDois,
        String operadorDois,
        String campoTres
) {

    public static CadastrarRegraResponse aPartirDe(String id, String nome, String descricao, String campoUm, String operadorUm, String campoDois, String operadorDois, String campoTres) {
        return new CadastrarRegraResponse(id, nome, descricao, campoUm, operadorUm, campoDois, operadorDois, campoTres);
    }
}
