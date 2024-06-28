package com.sistran.fastclaims.application.natureza.pesquisar.lista;

import com.sistran.fastclaims.domain.natureza.Natureza;
import com.sistran.fastclaims.domain.natureza.NaturezaID;

public record ListarNaturezasOutput(NaturezaID id, String nome, String codigoNatureza) {

    public static ListarNaturezasOutput aPartirDe(final Natureza natureza) {
        return new ListarNaturezasOutput(natureza.getId(), natureza.getNome(), natureza.getCodigoNatureza());
    }
}
