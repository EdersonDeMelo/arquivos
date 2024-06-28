package com.sistran.fastclaims.application.dominio.pesquisar.id;

import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class PesquisarDominioPorIdUseCase extends UseCase<String, Either<Notification, PesquisarDominioPorIdOutput>> {
}
