package com.sistran.fastclaims.application.dominiovalor.cadastrar;

import com.sistran.fastclaims.domain.dominio.Dominio;
import com.sistran.fastclaims.domain.dominio.DominioID;
import com.sistran.fastclaims.domain.dominiovalor.DominioValor;

import java.util.List;

public record CadastrarDominioValorOutput(DominioID id,
                                          List<DominioValor> valores) {

    public static CadastrarDominioValorOutput aPartirDe(final Dominio dominio) {
        return new CadastrarDominioValorOutput(dominio.getId(), dominio.getValores());
    }
}
