package com.sistran.fastclaims.infrastructure.trilha.models;

import com.sistran.fastclaims.application.trilha.atualizar.AtualizarTrilhaOutput;
import com.sistran.fastclaims.application.trilha.cadastrar.CadastrarTrilhaOutput;
import com.sistran.fastclaims.application.trilha.pesquisar.lista.ListarTrilhasOutput;

public record TrilhaResponse(
        String id,
        String nome,
        String descricao,
        boolean ativo,
        String fluxoId
) {

    public static TrilhaResponse aPartirDe(final String id, final String nome, final String descricao, final boolean ativo, final String fluxoId) {
        return new TrilhaResponse(id, nome, descricao, ativo, fluxoId);
    }

    public static TrilhaResponse aPartirDe(final ListarTrilhasOutput output) {
        return new TrilhaResponse(output.id().getValue(), output.nome(), output.descricao(), output.ativo(), output.fluxoId());
    }

    public static TrilhaResponse aPartirDe(final CadastrarTrilhaOutput output) {
        return new TrilhaResponse(output.id(), output.nome(), output.descricao(), true, output.fluxoId());
    }

    public static TrilhaResponse aPartirDe(AtualizarTrilhaOutput output) {
        return new TrilhaResponse(output.id(), output.nome(), output.descricao(), true, output.fluxoId());
    }
}

