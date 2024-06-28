package com.sistran.fastclaims.application.regra.cadastrar;

import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CadastrarRegraUseCase extends UseCase<CadastrarRegraCommand, Either<Notification, CadastrarRegraOutput>> {


}
