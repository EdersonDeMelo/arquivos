package com.sistran.fastclaims.application.fluxo.cadastrar;

import com.sistran.fastclaims.domain.fluxo.Fluxo;
import com.sistran.fastclaims.domain.fluxo.FluxoID;
import com.sistran.fastclaims.domain.natureza.NaturezaID;

public record CadastrarFluxoOutput(FluxoID id, String descricao, NaturezaID naturezaID) {

    public static CadastrarFluxoOutput aPartirDe(final Fluxo fluxo) {
        return new CadastrarFluxoOutput(fluxo.getId(), fluxo.getDescricao(), fluxo.getNaturezaId());
    }
}
