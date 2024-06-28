package com.sistran.fastclaims.infrastructure.fluxo.models;


import com.sistran.fastclaims.application.fluxo.pesquisar.id.PesquisarFluxoPorIdOutput;
import com.sistran.fastclaims.application.fluxo.pesquisar.lista.ListarFluxosOutput;

public record FluxoResponse(String id, String descricao, String naturezaId) {

    public static FluxoResponse aPartirDe(final String id, final String descricao, final String naturezaId) {
        return new FluxoResponse(id, descricao, naturezaId);
    }

    public static FluxoResponse aPartirDe(final PesquisarFluxoPorIdOutput output) {
        return new FluxoResponse(output.id().getValue(), output.descricao(), output.naturezaID().getValue());
    }

    public static FluxoResponse aPartirDe(final ListarFluxosOutput output) {
        return new FluxoResponse(output.id().getValue(), output.descricao(), output.naturezaID().getValue());
    }
}
