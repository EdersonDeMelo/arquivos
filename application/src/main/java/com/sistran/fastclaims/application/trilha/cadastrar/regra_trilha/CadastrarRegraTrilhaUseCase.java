package com.sistran.fastclaims.application.trilha.cadastrar.regra_trilha;

import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CadastrarRegraTrilhaUseCase extends UseCase<CadastrarRegraTrilhaCommand, Either<Notification, CadastrarRegraTrilhaOutput>> {

}
