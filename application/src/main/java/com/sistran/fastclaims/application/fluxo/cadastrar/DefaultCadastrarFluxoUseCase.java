package com.sistran.fastclaims.application.fluxo.cadastrar;

import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.fluxo.Fluxo;
import com.sistran.fastclaims.domain.fluxo.FluxoGateway;
import com.sistran.fastclaims.domain.natureza.Natureza;
import com.sistran.fastclaims.domain.natureza.NaturezaGateway;
import com.sistran.fastclaims.domain.natureza.NaturezaID;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.function.Supplier;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultCadastrarFluxoUseCase extends CadastrarFluxoUseCase {

    private final FluxoGateway fluxoGateway;
    private final NaturezaGateway naturezaGateway;

    public DefaultCadastrarFluxoUseCase(
            final FluxoGateway fluxoGateway,
            final NaturezaGateway naturezaGateway) {
        this.fluxoGateway = fluxoGateway;
        this.naturezaGateway = naturezaGateway;
    }

    @Override
    public Either<Notification, CadastrarFluxoOutput> executar(final CadastrarFluxoCommand comando) {

        final var naturezaId = NaturezaID.aPartirDe(comando.naturezaId());

        final var natureza = naturezaGateway.pesquisarPorId(naturezaId)
                .orElseThrow(naturezaNaoEncontrada(naturezaId));

        final var fluxo = Fluxo.novoFluxo(comando.descricao(), natureza.getId());

        Notification notification = Notification.create();

        fluxo.validate(notification);

        if (notification.hasErrors()) {
            return Left(notification);
        }

        return cadastrar(fluxo);
    }

    private Either<Notification, CadastrarFluxoOutput> cadastrar(final Fluxo fluxo) {
        return Try(() -> this.fluxoGateway.cadastrar(fluxo))
                .toEither()
                .bimap(Notification::create, CadastrarFluxoOutput::aPartirDe);
    }

    private static Supplier<DomainException> naturezaNaoEncontrada(final NaturezaID id) {
        return () -> NotFoundException.with(Natureza.class, id);
    }
}
