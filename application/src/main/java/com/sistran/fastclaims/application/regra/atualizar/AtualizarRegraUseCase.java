package com.sistran.fastclaims.application.regra.atualizar;

import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class AtualizarRegraUseCase extends UseCase<AtualizarRegraCommand, Either<Notification, AtualizarRegraOutput>> {

}
