package com.sistran.fastclaims.application.trilha.cadastrar;

import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.fluxo.Fluxo;
import com.sistran.fastclaims.domain.fluxo.FluxoGateway;
import com.sistran.fastclaims.domain.fluxo.FluxoID;
import com.sistran.fastclaims.domain.trilha.Trilha;
import com.sistran.fastclaims.domain.trilha.TrilhaGateway;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.function.Supplier;

import static io.vavr.API.Try;

public class DefaultCadastrarTrilhaUseCase extends CadastrarTrilhaUseCase {

    private final TrilhaGateway trilhaGateway;

    private final FluxoGateway fluxoGateway;

    public DefaultCadastrarTrilhaUseCase(final TrilhaGateway trilhaGateway, final FluxoGateway fluxoGateway) {
        this.trilhaGateway = trilhaGateway;
        this.fluxoGateway = fluxoGateway;
    }

    @Override
    public Either<Notification, CadastrarTrilhaOutput> executar(final CadastrarTrilhaCommand cadastrarTrilhaCommand) {

        final var fluxoID = validarFluxo(cadastrarTrilhaCommand.fluxoId());

        final var trilha = Trilha.novaTrilha(
                cadastrarTrilhaCommand.nome(),
                cadastrarTrilhaCommand.descricao(),
                FluxoID.from(fluxoID)
        );

        Notification notification = Notification.create();

        trilha.validate(notification);

        if (notification.hasErrors()) {
            return Either.left(notification);
        }

        return cadastrar(trilha);
    }

    private Either<Notification, CadastrarTrilhaOutput> cadastrar(final Trilha trilha) {
        return Try(() -> this.trilhaGateway.cadastrar(trilha))
                .toEither()
                .bimap(Notification::create, CadastrarTrilhaOutput::aPartirDe);
    }

    private static Supplier<DomainException> fluxoNaoEncontrado(final FluxoID id) {
        return () -> NotFoundException.with(Fluxo.class, id);
    }

    private String validarFluxo(final String id) {
        if (id == null || id.isEmpty()) {
            return "";
        }

        return fluxoGateway.pesquisarPorId(FluxoID.from(id))
                .map(f -> f.getId().getValue())
                .orElseThrow(fluxoNaoEncontrado(FluxoID.from(id)));
    }
}



