package com.sistran.fastclaims.application.colecao.cadastrar;

import com.sistran.fastclaims.domain.colecao.Colecao;
import com.sistran.fastclaims.domain.colecao.ColecaoID;

public record CadastrarColecaoOutput(ColecaoID id, String nome, String alias) {

    public static CadastrarColecaoOutput aPartirDe(final Colecao colecao) {
        return new CadastrarColecaoOutput(colecao.getId(), colecao.getNome(), colecao.getAlias());
    }
}
