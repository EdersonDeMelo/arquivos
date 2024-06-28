package com.sistran.fastclaims.application.fluxo.excluir;

import com.sistran.fastclaims.domain.fluxo.FluxoGateway;
import com.sistran.fastclaims.domain.fluxo.FluxoID;

public class DefaultExcluirFluxoUseCase extends ExcluirFluxoUseCase {

    private final FluxoGateway fluxoGateway;

    public DefaultExcluirFluxoUseCase(FluxoGateway fluxoGateway) {
        this.fluxoGateway = fluxoGateway;
    }

    @Override
    public void executar(final String anIn) {
        this.fluxoGateway.excluirPorId(FluxoID.from(anIn));
    }
}
