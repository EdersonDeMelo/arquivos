package com.sistran.fastclaims.application.fluxo.cadastrar;


import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CadastrarFluxoUseCase
        extends UseCase<CadastrarFluxoCommand, Either<Notification, CadastrarFluxoOutput>> {

}
