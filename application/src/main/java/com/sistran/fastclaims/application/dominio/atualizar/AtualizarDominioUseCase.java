package com.sistran.fastclaims.application.dominio.atualizar;

import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class AtualizarDominioUseCase extends UseCase<AtualizarDominioCommand, Either<Notification, AtualizarDominioOutput>> {
}
