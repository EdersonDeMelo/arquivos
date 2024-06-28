package com.sistran.fastclaims.application.segmento.cadastrar;

import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CadastrarSegmentoUseCase extends UseCase<CadastrarSegmentoCommand, Either<Notification, CadastrarSegmentoOutput>> {

}
