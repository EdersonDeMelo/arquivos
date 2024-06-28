package com.sistran.fastclaims.application.dominio.pesquisar.nome;

import com.sistran.fastclaims.domain.dominio.Dominio;
import com.sistran.fastclaims.domain.dominio.DominioID;

public record ListarDominiosOutput(
        DominioID id,
        String nome,
        String descricao
) {

    public static ListarDominiosOutput aPartirDe(final Dominio dominio) {
        return new ListarDominiosOutput(dominio.getId(), dominio.getNome(), dominio.getDescricao());
    }
}
