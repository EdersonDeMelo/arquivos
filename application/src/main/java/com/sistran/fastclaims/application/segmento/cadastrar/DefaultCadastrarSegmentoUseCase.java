package com.sistran.fastclaims.application.segmento.cadastrar;

import com.sistran.fastclaims.domain.segmento.Segmento;
import com.sistran.fastclaims.domain.segmento.SegmentoGateway;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

import static io.vavr.API.Try;

public class DefaultCadastrarSegmentoUseCase extends CadastrarSegmentoUseCase {

    private final SegmentoGateway segmentoGateway;

    public DefaultCadastrarSegmentoUseCase(final SegmentoGateway segmentoGateway) {
        this.segmentoGateway = segmentoGateway;
    }

    @Override
    public Either<Notification, CadastrarSegmentoOutput> executar(final CadastrarSegmentoCommand cadastrarSegmentoCommand) {

        final var notification = Notification.create();

        final var segmento = Segmento.novoSegmento(cadastrarSegmentoCommand.nome());

        segmento.validate(notification);

        if (notification.hasErrors()) {
            return Either.left(notification);
        }

        return this.cadastrar(segmento);
    }

    private Either<Notification, CadastrarSegmentoOutput> cadastrar(final Segmento segmento) {
        return Try(() -> this.segmentoGateway.cadastrar(segmento))
                .toEither()
                .bimap(Notification::create, CadastrarSegmentoOutput::aPartirDe);
    }
}
