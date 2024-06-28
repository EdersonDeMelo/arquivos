package com.sistran.fastclaims.application.colecaocampo.pesquisar.listar;

import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoGateway;
import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;

public class DefaultListarColecaoCampoUseCase extends ListarColecaoCampoUseCase {

    private final ColecaoCampoGateway colecaoCampoGateway;

    public DefaultListarColecaoCampoUseCase(final ColecaoCampoGateway colecaoCampoGateway) {
        this.colecaoCampoGateway = colecaoCampoGateway;
    }

    @Override
    public Pagination<ListarColecaoCampoOutput> executar(final SearchQuery query) {

        return colecaoCampoGateway.listar(query)
                .map(ListarColecaoCampoOutput::aPartirDe);
    }
}
