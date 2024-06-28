package com.sistran.fastclaims.application.colecao.cadastrar;

import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CadastrarColecaoUseCase
        extends UseCase<CadastrarColecaoCommand, Either<Notification, CadastrarColecaoOutput>> {
}
