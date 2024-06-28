package com.sistran.fastclaims.application.colecao.pesquisar.id;

import com.sistran.fastclaims.domain.colecao.Colecao;

public record PesquisarColecaoPorIdOutput(String id, String nome, String alias) {

    public static PesquisarColecaoPorIdOutput aPartirDe(final Colecao colecao) {
        return new PesquisarColecaoPorIdOutput(colecao.getId().getValue(), colecao.getNome(), colecao.getAlias());
    }
}
