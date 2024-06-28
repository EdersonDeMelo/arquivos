package com.sistran.fastclaims.application.trilha.atualizar;

import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.fluxo.FluxoGateway;
import com.sistran.fastclaims.domain.trilha.Trilha;
import com.sistran.fastclaims.domain.trilha.TrilhaGateway;
import com.sistran.fastclaims.domain.trilha.TrilhaID;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.function.Supplier;

import static io.vavr.API.Try;

public class DefaultAtualizarTrilhaUseCase extends AtualizarTrilhaUseCase {

    private final TrilhaGateway trilhaGateway;

    public DefaultAtualizarTrilhaUseCase(TrilhaGateway trilhaGateway, FluxoGateway fluxoGateway) {
        this.trilhaGateway = trilhaGateway;
    }

    @Override
    public Either<Notification, AtualizarTrilhaOutput> executar(AtualizarTrilhaCommand atualizarTrilhaCommand) {

        final var trilhaId = TrilhaID.from(atualizarTrilhaCommand.id());

        final var trilha = this.trilhaGateway.pesquisarPorId(trilhaId).orElseThrow(trilhaNaoEncontrada(trilhaId));

        trilha.atualizar(atualizarTrilhaCommand.nome(), atualizarTrilhaCommand.descricao());

        Notification notification = Notification.create();

        trilha.validate(notification);

        return notification.hasErrors() ? Either.left(notification) : editar(trilha);
    }

    private Either<Notification, AtualizarTrilhaOutput> editar(final Trilha trilha) {
        return Try(() -> this.trilhaGateway.atualizar(trilha))
                .toEither()
                .bimap(Notification::create, AtualizarTrilhaOutput::aPartirDe);
    }

    private static Supplier<DomainException> trilhaNaoEncontrada(final TrilhaID id) {
        return () -> NotFoundException.with(Trilha.class, id);
    }
}
