package com.sistran.fastclaims.application.colecaocampo.pesquisar.id;

import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampo;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoGateway;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoID;
import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;

import java.util.function.Supplier;

public class DefaultPesquisarColecaoCampoPorIdUseCase extends PesquisarColecaoCampoPorIdUseCase {

    private final ColecaoCampoGateway colecaoCampoGateway;

    public DefaultPesquisarColecaoCampoPorIdUseCase(final ColecaoCampoGateway colecaoCampoGateway) {
        this.colecaoCampoGateway = colecaoCampoGateway;
    }

    @Override
    public PesquisarColecaoCampoPorIdOutput executar(String id) {

        final var colecaoCampoId = ColecaoCampoID.from(id);

        return this.colecaoCampoGateway.pesquisarPorId(colecaoCampoId)
                .map(PesquisarColecaoCampoPorIdOutput::aPartirDe)
                .orElseThrow(colecaoCampoNaoEncontrado(colecaoCampoId));
    }

    private static Supplier<DomainException> colecaoCampoNaoEncontrado(final ColecaoCampoID id) {
        return () -> NotFoundException.with(ColecaoCampo.class, id);
    }
}
