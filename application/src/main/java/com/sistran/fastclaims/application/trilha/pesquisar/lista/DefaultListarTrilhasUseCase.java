package com.sistran.fastclaims.application.trilha.pesquisar.lista;

import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;
import com.sistran.fastclaims.domain.trilha.TrilhaGateway;

public class DefaultListarTrilhasUseCase extends ListarTrilhasUseCase {

    private final TrilhaGateway trilhaGateway;

    public DefaultListarTrilhasUseCase(final TrilhaGateway trilhaGateway) {
        this.trilhaGateway = trilhaGateway;
    }

    @Override
    public Pagination<ListarTrilhasOutput> executar(final SearchQuery query) {

        return trilhaGateway.listar(query)
                .map(ListarTrilhasOutput::aPartirDe);
    }
}




