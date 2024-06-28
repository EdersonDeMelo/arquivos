package com.sistran.fastclaims.application.trilha.excluir;

import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.trilha.Trilha;
import com.sistran.fastclaims.domain.trilha.TrilhaGateway;
import com.sistran.fastclaims.domain.trilha.TrilhaID;

import java.util.function.Supplier;

public class DefaultExcluirTrilhaUseCase extends ExcluirTrilhaUseCase {

    private final TrilhaGateway trilhaGateway;

    public DefaultExcluirTrilhaUseCase(final TrilhaGateway trilhaGateway) {
        this.trilhaGateway = trilhaGateway;
    }

    @Override
    public void executar(final String id) {

        final var trilhaId = TrilhaID.from(id);

        final var trilha = trilhaGateway.pesquisarPorId(trilhaId)
                .orElseThrow(trilhaNaoEncontrada(trilhaId));

        this.trilhaGateway.excluir(trilha.getId());
    }

    private static Supplier<DomainException> trilhaNaoEncontrada(final TrilhaID id) {
        return () -> NotFoundException.with(Trilha.class, id);
    }
}


