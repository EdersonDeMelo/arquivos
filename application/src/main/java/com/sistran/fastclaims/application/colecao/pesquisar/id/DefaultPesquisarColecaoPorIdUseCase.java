package com.sistran.fastclaims.application.colecao.pesquisar.id;

import com.sistran.fastclaims.domain.colecao.Colecao;
import com.sistran.fastclaims.domain.colecao.ColecaoGateway;
import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;

import java.util.function.Supplier;

public class DefaultPesquisarColecaoPorIdUseCase extends PesquisarColecaoPorIdUseCase {

    private final ColecaoGateway colecaoGateway;

    public DefaultPesquisarColecaoPorIdUseCase(final ColecaoGateway colecaoGateway) {
        this.colecaoGateway = colecaoGateway;
    }

    @Override
    public PesquisarColecaoPorIdOutput executar(final String id) {

        final var colecaoId = ColecaoID.from(id);

        return colecaoGateway.pesquisarPorId(colecaoId)
                .map(PesquisarColecaoPorIdOutput::aPartirDe)
                .orElseThrow(colecaoNaoEncontrada(colecaoId));
    }

    private static Supplier<DomainException> colecaoNaoEncontrada(final ColecaoID id) {
        return () -> NotFoundException.with(Colecao.class, id);
    }
}
