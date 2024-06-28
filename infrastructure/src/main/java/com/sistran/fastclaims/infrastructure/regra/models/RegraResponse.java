package com.sistran.fastclaims.infrastructure.regra.models;

import com.sistran.fastclaims.application.regra.pesquisar.lista.ListarRegrasOutput;

public record RegraResponse(
        String id,
        String nome,
        String descricao,
        String campoUm,
        String operadorUm,
        String campoDois,
        String operadorDois,
        String campoTres
) {

    public static RegraResponse aPartirDe(String id, String nome, String descricao, String campoUm, String operadorUm, String campoDois, String operadorDois, String campoTres) {
        return new RegraResponse(id, nome, descricao, campoUm, operadorUm, campoDois, operadorDois, campoTres);
    }

    public static RegraResponse aPartirDe(ListarRegrasOutput output) {
        return new RegraResponse(output.id(), output.nome(), output.descricao(), output.campoUm(), output.operadorUm(), output.campoDois(), output.operadorDois(), output.campoTres());
    }
}
