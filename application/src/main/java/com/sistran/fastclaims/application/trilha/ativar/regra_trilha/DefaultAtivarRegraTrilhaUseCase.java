package com.sistran.fastclaims.application.trilha.ativar.regra_trilha;

import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.trilha.Trilha;
import com.sistran.fastclaims.domain.trilha.TrilhaGateway;
import com.sistran.fastclaims.domain.trilha.TrilhaID;
import com.sistran.fastclaims.domain.validation.Error;

import java.util.function.Supplier;

public class DefaultAtivarRegraTrilhaUseCase extends AtivarRegraTrilhaUseCase {

    private final TrilhaGateway trilhaGateway;

    public DefaultAtivarRegraTrilhaUseCase(final TrilhaGateway trilhaGateway) {
        this.trilhaGateway = trilhaGateway;
    }

    @Override
    public void executar(final String trilhaId, final String regraId) {

        final var trilha = trilhaGateway.pesquisarPorId(TrilhaID.from(trilhaId))
                .orElseThrow(trilhaNaoEncontrada(TrilhaID.from(trilhaId)));

        trilha.getRegras().stream()
                .filter(regraTrilha -> regraTrilha.id().getValue().equals(regraId))
                .findFirst()
                .orElseThrow(() -> DomainException.with(new Error("Regra não encontrada na trilha")))
                .ativar();

        trilhaGateway.ativarRegraTrilha(trilha);
    }

    private static Supplier<DomainException> trilhaNaoEncontrada(final TrilhaID id) {
        return () -> NotFoundException.with(Trilha.class, id);
    }
}

