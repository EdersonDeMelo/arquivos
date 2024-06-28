package com.sistran.fastclaims.application.dominio.pesquisar.id;

import com.sistran.fastclaims.domain.dominio.Dominio;
import com.sistran.fastclaims.domain.dominio.DominioGateway;
import com.sistran.fastclaims.domain.dominio.DominioID;
import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.ParametroInexistenteException;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.function.Supplier;

public class DefaultPesquisarDominioPorIdUseCase extends PesquisarDominioPorIdUseCase {

    private final DominioGateway dominioGateway;

    public DefaultPesquisarDominioPorIdUseCase(final DominioGateway dominioGateway) {
        this.dominioGateway = dominioGateway;
    }

    @Override
    public Either<Notification, PesquisarDominioPorIdOutput> executar(String dominioId) {
        final var id = DominioID.from(dominioId);

        return dominioGateway.pesquisarPorId(id)
                .map(PesquisarDominioPorIdOutput::aPartirDe)
                .map(Either::<Notification, PesquisarDominioPorIdOutput>right)
                .orElseThrow(dominioNaoEncontrado(id));

    }

    private static Supplier<DomainException> dominioNaoEncontrado(final DominioID id) {
        return () -> ParametroInexistenteException.with(Dominio.class, id);
    }

}
