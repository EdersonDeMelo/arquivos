package com.sistran.fastclaims.application.trilha.pesquisar.lista;

import com.sistran.fastclaims.domain.trilha.Trilha;
import com.sistran.fastclaims.domain.trilha.TrilhaID;

public record ListarTrilhasOutput(
        TrilhaID id,
        String nome,
        String descricao,
        boolean ativo,
        String fluxoId
) {

    public static ListarTrilhasOutput aPartirDe(final Trilha trilha) {
        return new ListarTrilhasOutput(
                trilha.getId(),
                trilha.getNome(),
                trilha.getDescricao(),
                trilha.isAtivo(),
                trilha.getFluxoId().getValue()
        );
    }
}

