package com.sistran.fastclaims.application.dominio.cadastrar;

import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CadastrarDominioUseCase
        extends UseCase<CadastrarDominioCommand, Either<Notification, CadastrarDominioOutput>> {

}
