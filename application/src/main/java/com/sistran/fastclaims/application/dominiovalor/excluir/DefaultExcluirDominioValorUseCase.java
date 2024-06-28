package com.sistran.fastclaims.application.dominiovalor.excluir;

import com.sistran.fastclaims.domain.dominio.Dominio;
import com.sistran.fastclaims.domain.dominio.DominioGateway;
import com.sistran.fastclaims.domain.dominio.DominioID;
import com.sistran.fastclaims.domain.dominiovalor.DominioValor;
import com.sistran.fastclaims.domain.dominiovalor.DominioValorGateway;
import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.ParametroInexistenteException;

import java.util.function.Supplier;

public class DefaultExcluirDominioValorUseCase extends ExcluirDominioValorUseCase{

    private final DominioGateway dominioGateway;
    private final DominioValorGateway dominioValorGateway;

    public DefaultExcluirDominioValorUseCase(DominioGateway dominioGateway, DominioValorGateway dominioValorGateway) {
        this.dominioGateway = dominioGateway;
        this.dominioValorGateway = dominioValorGateway;
    }

    @Override
    public void executar(ExcluirDominioValorCommand command) {
        final var dominioId = DominioID.from(command.dominioId());

        final var dominio = dominioGateway.pesquisarPorId(dominioId)
                .orElseThrow(dominioNaoEncontrado(dominioId));

        final var dominioValor = dominio.getValores().stream()
                .filter(dv -> dv.getId().getValue().equals(command.dominioValorId()))
                .findFirst()
                .orElseThrow(() -> ParametroInexistenteException.com(DominioValor.class, command.dominioValorId()));

        dominioValorGateway.excluirDominioValorPorId(dominioValor);

    }

    private static Supplier<DomainException> dominioNaoEncontrado(final DominioID id) {
        return () -> ParametroInexistenteException.with(Dominio.class, id);
    }

}
