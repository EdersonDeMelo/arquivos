package com.sistran.fastclaims.application.trilha.pesquisar.id;

import com.sistran.fastclaims.domain.trilha.TrilhaID;

public record PesquisarTrilhaPorIdOutput(
        TrilhaID id,
        String nome,
        String descricao,
        boolean ativo,
        String fluxoId
) {

    public static PesquisarTrilhaPorIdOutput aPartirDe(
            final TrilhaID id,
            final String nome,
            final String descricao,
            final boolean ativo,
            final String fluxoId
    ) {
        return new PesquisarTrilhaPorIdOutput(id, nome, descricao, ativo, fluxoId);
    }
}

