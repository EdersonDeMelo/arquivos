package com.sistran.fastclaims.application.grupousuario.cadastrar;

import com.sistran.fastclaims.domain.grupousuario.GrupoUsuario;
import com.sistran.fastclaims.domain.grupousuario.GrupoUsuarioGateway;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

import static io.vavr.API.Try;

public class DefaultCadastrarGrupoUsuarioUseCase extends CadastrarGrupoUsuarioUseCase {

    private final GrupoUsuarioGateway grupoUsuarioGateway;

    public DefaultCadastrarGrupoUsuarioUseCase(GrupoUsuarioGateway grupoUsuarioGateway) {
        this.grupoUsuarioGateway = grupoUsuarioGateway;
    }

    @Override
    public Either<Notification, CadastrarGrupoUsuarioOutput> executar(CadastrarGrupoUsuarioCommand cadastrarGrupoUsuarioCommand) {
        final var grupoUsuario = GrupoUsuario.novoGrupoUsuario(
                cadastrarGrupoUsuarioCommand.nome());

        Notification notification = Notification.create();

        grupoUsuario.validate(notification);

        if (notification.hasErrors()) {
            return Either.left(notification);
        }

        return cadastrar(grupoUsuario);
    }

    private Either<Notification, CadastrarGrupoUsuarioOutput> cadastrar(final GrupoUsuario grupoUsuario) {
        return Try(() -> this.grupoUsuarioGateway.cadastrar(grupoUsuario))
                .toEither()
                .bimap(Notification::create, CadastrarGrupoUsuarioOutput::aPartirDe);
    }

}
