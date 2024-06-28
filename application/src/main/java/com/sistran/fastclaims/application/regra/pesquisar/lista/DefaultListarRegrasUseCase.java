package com.sistran.fastclaims.application.regra.pesquisar.lista;

import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;
import com.sistran.fastclaims.domain.regra.RegraGateway;

public class DefaultListarRegrasUseCase extends ListarRegrasUseCase {

    private final RegraGateway regraGateway;

    public DefaultListarRegrasUseCase(final RegraGateway regraGateway) {
        this.regraGateway = regraGateway;
    }

    @Override
    public Pagination<ListarRegrasOutput> executar(SearchQuery query) {
        return regraGateway.listar(query)
                .map(ListarRegrasOutput::aPartirDe);
    }
}
