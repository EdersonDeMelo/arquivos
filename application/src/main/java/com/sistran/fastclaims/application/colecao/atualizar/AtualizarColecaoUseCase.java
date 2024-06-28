package com.sistran.fastclaims.application.colecao.atualizar;

import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class AtualizarColecaoUseCase extends
        UseCase<AtualizarColecaoCommand, Either<Notification, AtualizarColecaoOutput>> {
}
