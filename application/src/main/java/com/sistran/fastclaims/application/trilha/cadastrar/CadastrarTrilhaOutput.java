package com.sistran.fastclaims.application.trilha.cadastrar;

import com.sistran.fastclaims.domain.trilha.Trilha;

public record CadastrarTrilhaOutput(
        String id,
        String nome,
        String descricao,
        String fluxoId
) {

    public static CadastrarTrilhaOutput aPartirDe(final Trilha trilha) {
        return new CadastrarTrilhaOutput(trilha.getId().getValue(), trilha.getNome(), trilha.getDescricao(), trilha.getFluxoId().getValue());
    }
}
