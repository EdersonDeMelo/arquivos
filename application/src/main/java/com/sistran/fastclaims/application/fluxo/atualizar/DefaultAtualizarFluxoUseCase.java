package com.sistran.fastclaims.application.fluxo.atualizar;

import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.fluxo.Fluxo;
import com.sistran.fastclaims.domain.fluxo.FluxoGateway;
import com.sistran.fastclaims.domain.fluxo.FluxoID;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.function.Supplier;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultAtualizarFluxoUseCase extends AtualizarFluxoUseCase {

    private final FluxoGateway fluxoGateway;

    public DefaultAtualizarFluxoUseCase(final FluxoGateway fluxoGateway) {
        this.fluxoGateway = fluxoGateway;
    }

    @Override
    public Either<Notification, AtualizarFluxoOutput> executar(final AtualizarFluxoCommand comando) {

        final var fluxoId = FluxoID.from(comando.id());

        final var fluxo = fluxoGateway.pesquisarPorId(fluxoId)
                .orElseThrow(fluxoNaoEncontrado(fluxoId));

        fluxo.atualizar(comando.descricao());

        Notification notification = Notification.create();

        fluxo.validate(notification);

        if (notification.hasErrors()) {
            return Left(notification);
        }

        return atualizar(fluxo);
    }

    private static Supplier<DomainException> fluxoNaoEncontrado(final FluxoID id) {
        return () -> NotFoundException.with(Fluxo.class, id);
    }

    private Either<Notification, AtualizarFluxoOutput> atualizar(final Fluxo fluxo) {
        return Try(() -> this.fluxoGateway.atualizar(fluxo))
                .toEither()
                .bimap(Notification::create, AtualizarFluxoOutput::aPartirDe);
    }
}