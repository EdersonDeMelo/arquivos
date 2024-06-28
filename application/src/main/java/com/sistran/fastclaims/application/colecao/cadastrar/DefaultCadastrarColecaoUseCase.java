package com.sistran.fastclaims.application.colecao.cadastrar;


import com.sistran.fastclaims.domain.colecao.Colecao;
import com.sistran.fastclaims.domain.colecao.ColecaoGateway;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

import static io.vavr.API.Try;

public class DefaultCadastrarColecaoUseCase extends CadastrarColecaoUseCase {

    private final ColecaoGateway colecaoGateway;

    public DefaultCadastrarColecaoUseCase(final ColecaoGateway colecaoGateway) {
        this.colecaoGateway = colecaoGateway;
    }

    @Override
    public Either<Notification, CadastrarColecaoOutput> executar(CadastrarColecaoCommand cadastrarColecaoCommand) {

        final var colecao = Colecao.novaColecao(cadastrarColecaoCommand.nome(), cadastrarColecaoCommand.alias());

        Notification notification = Notification.create();

        colecao.validate(notification);

        if (notification.hasErrors()) {
            return Either.left(notification);
        }

        return cadastrar(colecao);
    }

    private Either<Notification, CadastrarColecaoOutput> cadastrar(final Colecao colecao) {

        return Try(() -> this.colecaoGateway.cadastrar(colecao))
                .toEither()
                .bimap(Notification::create, CadastrarColecaoOutput::aPartirDe);
    }
}
