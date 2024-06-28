package com.sistran.fastclaims.application.dominio.pesquisar.nome;

import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class PesquisarDominioPorNomeUseCase
        extends UseCase<String, Either<Notification, ListarDominiosOutput>> {
}
