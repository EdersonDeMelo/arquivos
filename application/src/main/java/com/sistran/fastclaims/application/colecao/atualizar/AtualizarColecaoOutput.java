package com.sistran.fastclaims.application.colecao.atualizar;

import com.sistran.fastclaims.domain.colecao.Colecao;

public record AtualizarColecaoOutput(String id, String nome, String alias) {

    public static AtualizarColecaoOutput aPartirDe(final Colecao colecao) {
        return new AtualizarColecaoOutput(colecao.getId().getValue(), colecao.getNome(), colecao.getAlias());
    }
}
