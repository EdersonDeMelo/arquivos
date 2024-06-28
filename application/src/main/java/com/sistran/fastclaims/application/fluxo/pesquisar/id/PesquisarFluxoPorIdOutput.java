package com.sistran.fastclaims.application.fluxo.pesquisar.id;

import com.sistran.fastclaims.domain.fluxo.Fluxo;
import com.sistran.fastclaims.domain.fluxo.FluxoID;
import com.sistran.fastclaims.domain.natureza.NaturezaID;

public record PesquisarFluxoPorIdOutput(FluxoID id, String descricao, NaturezaID naturezaID) {

    public static PesquisarFluxoPorIdOutput aPartirDe(final Fluxo fluxo) {
        return new PesquisarFluxoPorIdOutput(fluxo.getId(), fluxo.getDescricao(), fluxo.getNaturezaId());
    }
}

