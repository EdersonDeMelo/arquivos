package com.sistran.fastclaims.application.trilha.atualizar;

import com.sistran.fastclaims.domain.trilha.Trilha;

public record AtualizarTrilhaOutput(
        String id,
        String nome,
        String descricao,
        String fluxoId
) {
    public static AtualizarTrilhaOutput aPartirDe(final Trilha trilha) {
        return new AtualizarTrilhaOutput(trilha.getId().getValue(), trilha.getNome(), trilha.getDescricao(), trilha.getFluxoId().getValue());
    }
}
