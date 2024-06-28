package com.sistran.fastclaims.application.trilha.atualizar;

import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class AtualizarTrilhaUseCase extends
        UseCase<AtualizarTrilhaCommand, Either<Notification, AtualizarTrilhaOutput>> {
}
