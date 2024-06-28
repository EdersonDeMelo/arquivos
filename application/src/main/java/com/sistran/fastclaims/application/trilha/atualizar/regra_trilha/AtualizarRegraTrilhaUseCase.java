package com.sistran.fastclaims.application.trilha.atualizar.regra_trilha;

import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class AtualizarRegraTrilhaUseCase extends UseCase<AtualizarRegraTrilhaCommand, Either<Notification, AtualizarRegraTrilhaOutput>> {
}
