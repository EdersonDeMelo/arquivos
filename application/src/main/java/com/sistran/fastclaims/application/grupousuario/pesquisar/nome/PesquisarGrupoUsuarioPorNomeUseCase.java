package com.sistran.fastclaims.application.grupousuario.pesquisar.nome;

import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class PesquisarGrupoUsuarioPorNomeUseCase extends UseCase<String, Either<Notification, ListarGrupoUsuarioPorNomeOutput>> {
}
