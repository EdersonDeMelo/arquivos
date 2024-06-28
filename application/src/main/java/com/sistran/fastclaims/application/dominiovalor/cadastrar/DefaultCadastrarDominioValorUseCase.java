package com.sistran.fastclaims.application.dominiovalor.cadastrar;

import com.sistran.fastclaims.domain.dominio.Dominio;
import com.sistran.fastclaims.domain.dominio.DominioGateway;
import com.sistran.fastclaims.domain.dominio.DominioID;
import com.sistran.fastclaims.domain.dominiovalor.DominioValor;
import com.sistran.fastclaims.domain.exceptions.ParametroInexistenteException;
import com.sistran.fastclaims.domain.validation.ValidationHandler;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultCadastrarDominioValorUseCase extends CadastrarDominioValorUseCase {

    private final DominioGateway dominioGateway;

    public DefaultCadastrarDominioValorUseCase(DominioGateway dominioGateway) {
        this.dominioGateway = dominioGateway;
    }

    @Override
    public Either<Notification, CadastrarDominioValorOutput> executar(CadastrarDominioValorCommand command) {

        final var notification = Notification.create();
        notification.append(validadorDominioValor(command));

        if (notification.hasErrors()) {
            return Left(notification);
        } else {
            DominioID dominioId = DominioID.from(command.dominioid().getValue());
            Dominio dominio = getDominio(dominioId);

            DominioValor dominioValor = DominioValor.novoDominioValor(
                    command.codigoValor(),
                    command.nomeValor(),
                    dominioId);

            dominio.adicionarValor(dominioValor);

            notification.append(validadorDominio(dominio));
            if (notification.hasErrors()) {
                return Left(notification);
            }
            return cadastrarDominioValor(dominio);

        }
    }

    private Either<Notification, CadastrarDominioValorOutput> cadastrarDominioValor(Dominio dominio) {
        return Try(() -> dominioGateway.adicionarValor(dominio))
                .toEither()
                .bimap(Notification::create, CadastrarDominioValorOutput::aPartirDe);
    }

    private Dominio getDominio(DominioID dominioId) {
        return dominioGateway.pesquisarPorId(dominioId).orElseThrow(() ->
                ParametroInexistenteException.with(Dominio.class, dominioId));
    }

    private ValidationHandler validadorDominioValor(CadastrarDominioValorCommand command) {
        final var dominioValor = DominioValor.novoDominioValor(
                command.codigoValor(),
                command.nomeValor(),
                DominioID.from(command.dominioid().getValue()));
        final var notificacao = Notification.create();
        dominioValor.validate(notificacao);
        return notificacao;
    }

    private ValidationHandler validadorDominio(Dominio dominio) {
        final var dominioValidado = Dominio.novoDominio(
                dominio.getNome(),
                dominio.getDescricao(),
                dominio.getValores());
        final var notificacao = Notification.create();
        dominioValidado.validate(notificacao);
        return notificacao;
    }
}
