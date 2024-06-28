package com.sistran.fastclaims.application.grupousuario.pesquisar.id;

import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class PesquisarGrupoUsuarioPorIdUseCase extends UseCase<String, Either<Notification, PesquisarGrupoUsuarioPorIdOutput>> {
}
