package com.sistran.fastclaims.application.dominio.atualizar;

import com.sistran.fastclaims.domain.dominio.Dominio;
import com.sistran.fastclaims.domain.dominio.DominioGateway;
import com.sistran.fastclaims.domain.dominio.DominioID;
import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.ParametroInexistenteException;
import com.sistran.fastclaims.domain.utils.FormatStringUtils;
import com.sistran.fastclaims.domain.validation.Error;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.function.Supplier;

import static io.vavr.API.Try;

public class DefaultAtualizarDominioUseCase extends AtualizarDominioUseCase {

    private final DominioGateway dominioGateway;

    public DefaultAtualizarDominioUseCase(DominioGateway dominioGateway) {
        this.dominioGateway = dominioGateway;
    }

    @Override
    public Either<Notification, AtualizarDominioOutput> executar(AtualizarDominioCommand atualizarDominioCommand) {

        final var dominioId = DominioID.from(atualizarDominioCommand.id());

        final var dominio = dominioGateway.pesquisarPorId(dominioId)
                .orElseThrow(dominioNaoEncontrado(dominioId));

        final var nomeFormatado = FormatStringUtils.formatString(atualizarDominioCommand.nome());
        final var nomeDominio = dominioGateway.pesquisarPorNome(nomeFormatado).orElse(null);
        if (nomeDominio != null) {
            return Either.left(Notification.create(new Error("Nome do domínio já existe")));
        }

        Dominio dominioAtualizado = dominio.atualizar(nomeFormatado, atualizarDominioCommand.descricao());
        Notification notification = Notification.create();

        dominioAtualizado.validate(notification);

        if (notification.hasErrors()) {
            return Either.left(notification);
        }
        return atualizarDominio(dominioAtualizado);
    }

    private Either<Notification, AtualizarDominioOutput> atualizarDominio(final Dominio dominio) {
        return Try(() -> this.dominioGateway.atualizar(dominio))
                .toEither()
                .bimap(Notification::create, AtualizarDominioOutput::aPartirDe);
    }

    private static Supplier<DomainException> dominioNaoEncontrado(final DominioID id) {
        return () -> ParametroInexistenteException.with(Dominio.class, id);
    }
}
