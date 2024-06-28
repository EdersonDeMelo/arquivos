package com.sistran.fastclaims.application.trilha.pesquisar.id;

import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.trilha.Trilha;
import com.sistran.fastclaims.domain.trilha.TrilhaGateway;
import com.sistran.fastclaims.domain.trilha.TrilhaID;

import java.util.function.Supplier;

public class DefaultPesquisarTrilhaPorIdUseCase extends PesquisarTrilhaPorIdUseCase {

    private final TrilhaGateway trilhaGateway;

    public DefaultPesquisarTrilhaPorIdUseCase(final TrilhaGateway trilhaGateway) {
        this.trilhaGateway = trilhaGateway;
    }

    @Override
    public PesquisarTrilhaPorIdOutput executar(final String id) {

        final var trilhaId = TrilhaID.from(id);

        return trilhaGateway.pesquisarPorId(trilhaId)
                .map(trilha -> PesquisarTrilhaPorIdOutput.aPartirDe(
                        trilha.getId(),
                        trilha.getNome(),
                        trilha.getDescricao(),
                        trilha.isAtivo(),
                        trilha.getFluxoId().getValue()
                ))
                .orElseThrow(trilhaNaoEncontrada(trilhaId));
    }

    private static Supplier<DomainException> trilhaNaoEncontrada(final TrilhaID id) {
        return () -> NotFoundException.with(Trilha.class, id);
    }
}

