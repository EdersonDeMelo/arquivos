package com.sistran.fastclaims.application.dominiovalor.cadastrar;

import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CadastrarDominioValorUseCase extends UseCase<CadastrarDominioValorCommand, Either<Notification, CadastrarDominioValorOutput>> {
}
