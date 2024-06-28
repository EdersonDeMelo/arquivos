package com.sistran.fastclaims.application.colecao.atualizar;

import com.sistran.fastclaims.domain.colecao.Colecao;
import com.sistran.fastclaims.domain.colecao.ColecaoGateway;
import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.function.Supplier;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultAtualizarColecaoUseCase extends AtualizarColecaoUseCase {

    private final ColecaoGateway colecaoGateway;

    public DefaultAtualizarColecaoUseCase(final ColecaoGateway colecaoGateway) {
        this.colecaoGateway = colecaoGateway;
    }

    @Override
    public Either<Notification, AtualizarColecaoOutput> executar(AtualizarColecaoCommand atualizarColecaoCommand) {

        final var colecaoId = ColecaoID.from(atualizarColecaoCommand.id());

        final var colecao = colecaoGateway.pesquisarPorId(colecaoId)
                .orElseThrow(colecaoNaoEncontrado(colecaoId));

        colecao.atualizar(atualizarColecaoCommand.alias());

        Notification notification = Notification.create();

        colecao.validate(notification);

        if (notification.hasErrors()) {
            return Left(notification);
        }

        return atualizar(colecao);
    }

    private Supplier<DomainException> colecaoNaoEncontrado(ColecaoID id) {
        return () -> NotFoundException.with(Colecao.class, id);
    }

    private Either<Notification, AtualizarColecaoOutput> atualizar(final Colecao colecao) {
        return Try(() -> this.colecaoGateway.atualizar(colecao))
                .toEither()
                .bimap(Notification::create, AtualizarColecaoOutput::aPartirDe);
    }
}
