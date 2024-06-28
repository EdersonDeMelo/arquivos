package com.sistran.fastclaims.infrastructure.regra.models;

public record AtualizarRegraResponse(
        String id,
        String nome,
        String descricao,
        String campoUm,
        String operadorUm,
        String campoDois,
        String operadorDois,
        String campoTres
) {

    public static AtualizarRegraResponse aPartirDe(String id, String nome, String descricao, String campoUm, String operadorUm, String campoDois, String operadorDois, String campoTres) {
        return new AtualizarRegraResponse(id, nome, descricao, campoUm, operadorUm, campoDois, operadorDois, campoTres);
    }
}


