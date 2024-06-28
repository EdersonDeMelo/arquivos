package com.sistran.fastclaims.application.trilha.atualizar.regra_trilha;

import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.regra.Regra;
import com.sistran.fastclaims.domain.regra.RegraGateway;
import com.sistran.fastclaims.domain.regra.RegraID;
import com.sistran.fastclaims.domain.regra.TipoAcao;
import com.sistran.fastclaims.domain.trilha.RegraTrilha;
import com.sistran.fastclaims.domain.trilha.Trilha;
import com.sistran.fastclaims.domain.trilha.TrilhaGateway;
import com.sistran.fastclaims.domain.trilha.TrilhaID;
import com.sistran.fastclaims.domain.validation.Error;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.function.Supplier;

import static io.vavr.API.Try;

public class DefaultAtualizarRegraTrilhaUseCase extends AtualizarRegraTrilhaUseCase {

    private final RegraGateway regraGateway;
    private final TrilhaGateway trilhaGateway;

    public DefaultAtualizarRegraTrilhaUseCase(
            final RegraGateway regraGateway,
            final TrilhaGateway trilhaGateway) {
        this.regraGateway = regraGateway;
        this.trilhaGateway = trilhaGateway;
    }

    @Override
    public Either<Notification, AtualizarRegraTrilhaOutput> executar(final AtualizarRegraTrilhaCommand atualizarRegraTrilhaCommand) {

        obterRegra(RegraID.from(atualizarRegraTrilhaCommand.regraId()));

        final var trilha = obterTrilha(TrilhaID.from(atualizarRegraTrilhaCommand.trilhaId()));

        trilha.getRegras().stream()
                .filter(rt -> rt.id().equals(RegraID.from(atualizarRegraTrilhaCommand.regraId())))
                .findFirst()
                .orElseThrow(() -> DomainException.with(new Error("Regra não encontrada na trilha!")));

        trilha.getRegras().removeIf(rt -> rt.id().equals(RegraID.from(atualizarRegraTrilhaCommand.regraId())));

        final var regraTrilha = montarRegraTrilha(atualizarRegraTrilhaCommand);

        trilha.getRegras().add(regraTrilha);

        return atualizarRegraTrilha(trilha, regraTrilha);
    }

    private static Supplier<DomainException> regraNaoEncontrada(final RegraID id) {
        return () -> NotFoundException.with(Regra.class, id);
    }

    private static Supplier<DomainException> trilhaNaoEncontrada(final TrilhaID id) {
        return () -> NotFoundException.with(Trilha.class, id);
    }

    private Either<Notification, AtualizarRegraTrilhaOutput> atualizarRegraTrilha(final Trilha trilha, final RegraTrilha regraTrilha) {
        return Try(() -> this.trilhaGateway.atualizarRegraTrilha(trilha, regraTrilha))
                .toEither()
                .bimap(Notification::create, AtualizarRegraTrilhaOutput::aPartirDe);
    }

    private Trilha obterTrilha(final TrilhaID id) {
        return trilhaGateway.pesquisarPorId(id)
                .orElseThrow(trilhaNaoEncontrada(id));
    }

    private Regra obterRegra(final RegraID id) {
        return regraGateway.pesquisarPorId(id)
                .orElseThrow(regraNaoEncontrada(id));
    }

    private RegraTrilha montarRegraTrilha(final AtualizarRegraTrilhaCommand command) {
        return RegraTrilha.novaRegraTrilha(
                RegraID.from(command.regraId()),
                command.resultadoEsperado(),
                TipoAcao.of(command.tipoAcao()).orElseThrow(() -> DomainException.with(new Error("Tipo de ação nulo ou inválido."))),
                command.ativa()
        );
    }
}


