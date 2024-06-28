package com.sistran.fastclaims.application.trilha.excluir.regra_trilha;

import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.regra.Regra;
import com.sistran.fastclaims.domain.regra.RegraGateway;
import com.sistran.fastclaims.domain.regra.RegraID;
import com.sistran.fastclaims.domain.trilha.Trilha;
import com.sistran.fastclaims.domain.trilha.TrilhaGateway;
import com.sistran.fastclaims.domain.trilha.TrilhaID;
import com.sistran.fastclaims.domain.validation.Error;

import java.util.function.Supplier;

public class DefaultExcluirRegraTrilhaUseCase extends ExcluirRegraTrilhaUseCase {

    private final TrilhaGateway trilhaGateway;
    private final RegraGateway regraGateway;

    public DefaultExcluirRegraTrilhaUseCase(final TrilhaGateway trilhaGateway, final RegraGateway regraGateway) {
        this.trilhaGateway = trilhaGateway;
        this.regraGateway = regraGateway;
    }

    @Override
    public void executar(final String trilhaId, final String regraId) {

        final var idTrilha = TrilhaID.from(trilhaId);
        final var idRegra = RegraID.from(regraId);

        final var trilha = trilhaGateway.pesquisarPorId(idTrilha)
                .orElseThrow(trilhaNaoEncontrada(idTrilha));

        final var regra = regraGateway.pesquisarPorId(idRegra)
                .orElseThrow(regraNaoEncontrada(idRegra));

        final var trilhaRegra = trilha.getRegras().stream()
                .filter(rt -> rt.id().equals(RegraID.from(regraId)))
                .findFirst()
                .orElseThrow(() -> DomainException.with(new Error("Regra não encontrada na trilha!")));

        trilha.getRegras().remove(trilhaRegra);

        final var regraTrilha = regra.getTrilhas().stream()
                .filter(rt -> rt.equals(TrilhaID.from(trilhaId)))
                .findFirst()
                .orElseThrow(() -> DomainException.with(new Error("Trilha não encontrada na regra!")));

        regra.getTrilhas().remove(regraTrilha);

        trilhaGateway.atualizar(trilha);
        regraGateway.atualizar(regra);
    }

    private static Supplier<DomainException> trilhaNaoEncontrada(final TrilhaID id) {
        return () -> NotFoundException.with(Trilha.class, id);
    }

    private static Supplier<DomainException> regraNaoEncontrada(final RegraID id) {
        return () -> NotFoundException.with(Regra.class, id);
    }
}
