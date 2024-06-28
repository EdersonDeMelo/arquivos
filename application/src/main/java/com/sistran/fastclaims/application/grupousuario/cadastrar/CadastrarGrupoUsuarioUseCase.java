package com.sistran.fastclaims.application.grupousuario.cadastrar;

import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CadastrarGrupoUsuarioUseCase
        extends UseCase<CadastrarGrupoUsuarioCommand, Either<Notification, CadastrarGrupoUsuarioOutput>> {
}
