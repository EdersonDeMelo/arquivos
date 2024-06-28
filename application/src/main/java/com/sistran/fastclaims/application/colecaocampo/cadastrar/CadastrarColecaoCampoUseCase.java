package com.sistran.fastclaims.application.colecaocampo.cadastrar;

import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CadastrarColecaoCampoUseCase
        extends UseCase<CadastrarColecaoCampoCommand, Either<Notification, CadastrarColecaoCampoOutput>> {
}
