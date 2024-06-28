package com.sistran.fastclaims.application.operador.pesquisar.id;

import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.operador.Operador;
import com.sistran.fastclaims.domain.operador.OperadorGateway;
import com.sistran.fastclaims.domain.operador.OperadorID;

import java.util.function.Supplier;

public class DefaultPesquisarOperadorPorIdUseCase extends PesquisarOperadorPorIdUseCase {

    private final OperadorGateway operadorGateway;

    public DefaultPesquisarOperadorPorIdUseCase(final OperadorGateway operadorGateway) {
        this.operadorGateway = operadorGateway;
    }

    @Override
    public PesquisarOperadorPorIdOutput executar(final String id) {

        final var operadorId = OperadorID.from(id);

        return operadorGateway.pesquisarPorId(OperadorID.from(id))
                .map(PesquisarOperadorPorIdOutput::aPartirDe)
                .orElseThrow(operadorNaoEncontrado(operadorId));

    }

    private Supplier<DomainException> operadorNaoEncontrado(OperadorID operadorId) {
        return () -> NotFoundException.with(Operador.class, operadorId);
    }
}
