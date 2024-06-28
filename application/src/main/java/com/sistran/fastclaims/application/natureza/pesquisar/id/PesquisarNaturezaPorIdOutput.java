package com.sistran.fastclaims.application.natureza.pesquisar.id;


import com.sistran.fastclaims.domain.natureza.Natureza;
import com.sistran.fastclaims.domain.natureza.NaturezaID;

public record PesquisarNaturezaPorIdOutput(NaturezaID id, String nome, String codigoNatureza) {

    public static PesquisarNaturezaPorIdOutput aPartirDe(final Natureza natureza) {
        return new PesquisarNaturezaPorIdOutput(natureza.getId(), natureza.getNome(), natureza.getCodigoNatureza());
    }
}
