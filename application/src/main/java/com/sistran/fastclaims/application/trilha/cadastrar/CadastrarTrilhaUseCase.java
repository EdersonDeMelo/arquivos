package com.sistran.fastclaims.application.trilha.cadastrar;

import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CadastrarTrilhaUseCase
        extends UseCase<CadastrarTrilhaCommand, Either<Notification, CadastrarTrilhaOutput>> {
}
