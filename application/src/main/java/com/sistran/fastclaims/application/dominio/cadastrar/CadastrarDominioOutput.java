package com.sistran.fastclaims.application.dominio.cadastrar;

import com.sistran.fastclaims.domain.dominio.Dominio;
import com.sistran.fastclaims.domain.dominio.DominioID;
import com.sistran.fastclaims.domain.dominiovalor.DominioValor;

import java.time.Instant;
import java.util.List;

public record CadastrarDominioOutput(DominioID id, String nome, String descricao,
                                     Instant dataCadastro, List<DominioValor> valores) {

    public static CadastrarDominioOutput aPartirDe(final Dominio dominio) {
        return new CadastrarDominioOutput(dominio.getId(), dominio.getNome(), dominio.getDescricao(), dominio.getDataCadastro()
                , dominio.getValores());
    }
}
