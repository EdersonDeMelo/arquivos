package com.sistran.fastclaims.application.segmento.atualizar;

import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.segmento.Segmento;
import com.sistran.fastclaims.domain.segmento.SegmentoGateway;
import com.sistran.fastclaims.domain.segmento.SegmentoID;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.function.Supplier;

import static io.vavr.API.Try;

public class DefaultAtualizarSegmentoUseCase extends AtualizarSegmentoUseCase {

    private final SegmentoGateway segmentoGateway;

    public DefaultAtualizarSegmentoUseCase(final SegmentoGateway segmentoGateway) {
        this.segmentoGateway = segmentoGateway;
    }

    @Override
    public Either<Notification, AtualizarSegmentoOutput> executar(AtualizarSegmentoCommand atualizarSegmentoCommand) {

        final var notification = Notification.create();

        final var segmento = this.segmentoGateway.pesquisarPorId(SegmentoID.aPartirDe(atualizarSegmentoCommand.id()))
                .orElseThrow(segmentoNaoEncontrado(SegmentoID.aPartirDe(atualizarSegmentoCommand.id())));

        segmento.atualizar(atualizarSegmentoCommand.nome());

        segmento.validate(notification);

        if (notification.hasErrors()) {
            return Either.left(notification);
        }

        return this.atualizar(segmento);
    }

    private Either<Notification, AtualizarSegmentoOutput> atualizar(final Segmento segmento) {
        return Try(() -> this.segmentoGateway.atualizar(segmento))
                .toEither()
                .bimap(Notification::create, AtualizarSegmentoOutput::aPartirDe);
    }

    private static Supplier<DomainException> segmentoNaoEncontrado(final SegmentoID id) {
        return () -> NotFoundException.with(Segmento.class, id);
    }
}
