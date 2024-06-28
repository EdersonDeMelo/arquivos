package com.sistran.fastclaims.application.fluxo.pesquisar.lista;

import com.sistran.fastclaims.domain.fluxo.Fluxo;
import com.sistran.fastclaims.domain.fluxo.FluxoID;
import com.sistran.fastclaims.domain.natureza.NaturezaID;

public record ListarFluxosOutput(FluxoID id, String descricao, NaturezaID naturezaID) {

    public static ListarFluxosOutput aPartirDe(final Fluxo fluxo) {
        return new ListarFluxosOutput(fluxo.getId(), fluxo.getDescricao(), fluxo.getNaturezaId());
    }
}
