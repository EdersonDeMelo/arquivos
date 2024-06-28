package com.sistran.fastclaims.application.segmento.atualizar;

import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class AtualizarSegmentoUseCase extends UseCase<AtualizarSegmentoCommand, Either<Notification, AtualizarSegmentoOutput>> {
}
