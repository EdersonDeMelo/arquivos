package com.sistran.fastclaims.application.dominio.cadastrar;

import com.sistran.fastclaims.domain.dominio.Dominio;
import com.sistran.fastclaims.domain.dominio.DominioGateway;
import com.sistran.fastclaims.domain.utils.FormatStringUtils;
import com.sistran.fastclaims.domain.validation.Error;
import com.sistran.fastclaims.domain.validation.ValidationHandler;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultCadastrarDominioUseCase extends CadastrarDominioUseCase {

    private final DominioGateway dominioGateway;


    public DefaultCadastrarDominioUseCase(final DominioGateway dominioGateway) {
        this.dominioGateway = dominioGateway;
    }

    @Override
    public Either<Notification, CadastrarDominioOutput> executar(final CadastrarDominioCommand cadastrarDominioCommand) {

        final var notification = Notification.create();
        notification.append(validadorDominio(cadastrarDominioCommand));

        if (notification.hasErrors()) {
            return Left(notification);
        } else {
            final var nomeFormatado = FormatStringUtils.formatString(cadastrarDominioCommand.nome());
            final var nomeDominioJaExistente = dominioGateway.pesquisarPorNome(nomeFormatado).orElse(null);

            if (nomeDominioJaExistente != null) {
                return Either.left(Notification.create(new Error("Nome do domínio já existe")));
            }

            final var dominio = Dominio.novoDominio(
                    nomeFormatado,
                    cadastrarDominioCommand.descricao(),
                    cadastrarDominioCommand.valores());

            dominio.validate(notification);

            return cadastrar(dominio);
        }
    }

    private Either<Notification, CadastrarDominioOutput> cadastrar(final Dominio dominio) {
        return Try(() -> this.dominioGateway.cadastrar(dominio))
                .toEither()
                .bimap(Notification::create, CadastrarDominioOutput::aPartirDe);
    }

    private ValidationHandler validadorDominio(CadastrarDominioCommand dominioCommand) {
        final var dominio = Dominio.novoDominio(
                dominioCommand.nome(),
                dominioCommand.descricao(),
                dominioCommand.valores());
        final var notificacao = Notification.create();
        dominio.validate(notificacao);
        return notificacao;
    }
}
