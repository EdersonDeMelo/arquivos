package com.sistran.fastclaims.infrastructure.natureza.models;

import com.sistran.fastclaims.application.natureza.pesquisar.id.PesquisarNaturezaPorIdOutput;
import com.sistran.fastclaims.application.natureza.pesquisar.lista.ListarNaturezasOutput;

public record NaturezaResponse(String id, String nome, String codigoNatureza) {

    public static NaturezaResponse aPartirDe(final String id, final String nome, final String codigoNatureza) {
        return new NaturezaResponse(id, nome, codigoNatureza);
    }

    public static NaturezaResponse aPartirDe(final PesquisarNaturezaPorIdOutput outPut) {
        return new NaturezaResponse(outPut.id().getValue(), outPut.nome(), outPut.codigoNatureza());
    }

    public static NaturezaResponse aPartirDe(final ListarNaturezasOutput outPut) {
        return new NaturezaResponse(outPut.id().getValue(), outPut.nome(), outPut.codigoNatureza());
    }

}
