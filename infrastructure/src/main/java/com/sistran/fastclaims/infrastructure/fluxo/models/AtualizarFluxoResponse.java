package com.sistran.fastclaims.infrastructure.fluxo.models;

public record AtualizarFluxoResponse(String id, String descricao) {

    public static AtualizarFluxoResponse aPartirDe(final String id, final String descricao) {
        return new AtualizarFluxoResponse(id, descricao);
    }


}
