package com.sistran.fastclaims.infrastructure.dominio.models;

import com.sistran.fastclaims.application.dominio.pesquisar.nome.ListarDominiosOutput;

public record ListarDominiosResponse(String id, String nome, String descricao) {

    public static ListarDominiosResponse aPartirDe(final String id, final String nome, final String descricao) {
        return new ListarDominiosResponse(id, nome, descricao);
    }

    public static ListarDominiosResponse aPartirDe(final ListarDominiosOutput output) {
        return new ListarDominiosResponse(output.id().getValue(), output.nome(), output.descricao());
    }
}
