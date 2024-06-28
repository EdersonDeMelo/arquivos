package com.sistran.fastclaims.application.fluxo.pesquisar.id;

import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.fluxo.Fluxo;
import com.sistran.fastclaims.domain.fluxo.FluxoGateway;
import com.sistran.fastclaims.domain.fluxo.FluxoID;

import java.util.function.Supplier;

public class DefaultPesquisarFluxoPorIdUseCase extends PesquisarFluxoPorIdUseCase {

    private final FluxoGateway fluxoGateway;

    public DefaultPesquisarFluxoPorIdUseCase(final FluxoGateway fluxoGateway) {
        this.fluxoGateway = fluxoGateway;
    }

    @Override
    public PesquisarFluxoPorIdOutput executar(final String id) {

        final var fluxoId = FluxoID.from(id);

        return fluxoGateway.pesquisarPorId(fluxoId)
                .map(PesquisarFluxoPorIdOutput::aPartirDe)
                .orElseThrow(fluxoNaoEncontrado(fluxoId));
    }

    private static Supplier<DomainException> fluxoNaoEncontrado(final FluxoID id) {
        return () -> NotFoundException.with(Fluxo.class, id);
    }
}

