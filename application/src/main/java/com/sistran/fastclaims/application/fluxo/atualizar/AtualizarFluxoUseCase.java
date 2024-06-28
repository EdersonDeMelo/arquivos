package com.sistran.fastclaims.application.fluxo.atualizar;

import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class AtualizarFluxoUseCase extends UseCase<AtualizarFluxoCommand, Either<Notification, AtualizarFluxoOutput>> {
}