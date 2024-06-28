package com.sistran.fastclaims.application.fluxo.atualizar;

import com.sistran.fastclaims.domain.fluxo.Fluxo;
import com.sistran.fastclaims.domain.fluxo.FluxoID;

public record AtualizarFluxoOutput(FluxoID id, String descricao) {

    public static AtualizarFluxoOutput aPartirDe(final Fluxo fluxo) {
        return new AtualizarFluxoOutput(fluxo.getId(), fluxo.getDescricao());
    }
}