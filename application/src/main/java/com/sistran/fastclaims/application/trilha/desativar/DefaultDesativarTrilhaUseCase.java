package com.sistran.fastclaims.application.trilha.desativar;

import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.trilha.Trilha;
import com.sistran.fastclaims.domain.trilha.TrilhaGateway;
import com.sistran.fastclaims.domain.trilha.TrilhaID;

import java.util.function.Supplier;

public class DefaultDesativarTrilhaUseCase extends DesativarTrilhaUseCase {

    private final TrilhaGateway trilhaGateway;

    public DefaultDesativarTrilhaUseCase(final TrilhaGateway trilhaGateway) {
        this.trilhaGateway = trilhaGateway;
    }

    @Override
    public void executar(final String id) {

        final var trilhaId = TrilhaID.from(id);

        final var trilha = trilhaGateway.pesquisarPorId(trilhaId)
                .orElseThrow(trilhaNaoEncontrada(trilhaId));

        trilha.desativar();
        trilhaGateway.desativar(trilha);
    }

    private static Supplier<DomainException> trilhaNaoEncontrada(final TrilhaID id) {
        return () -> NotFoundException.with(Trilha.class, id);
    }
}
