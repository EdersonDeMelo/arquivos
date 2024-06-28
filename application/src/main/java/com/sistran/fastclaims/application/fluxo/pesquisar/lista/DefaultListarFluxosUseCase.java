package com.sistran.fastclaims.application.fluxo.pesquisar.lista;

import com.sistran.fastclaims.domain.fluxo.FluxoGateway;
import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;

public class DefaultListarFluxosUseCase extends ListarFluxosUseCase {

    private final FluxoGateway fluxoGateway;

    public DefaultListarFluxosUseCase(final FluxoGateway fluxoGateway) {
        this.fluxoGateway = fluxoGateway;
    }

    @Override
    public Pagination<ListarFluxosOutput> executar(final SearchQuery query) {

        return fluxoGateway.listar(query)
                .map(ListarFluxosOutput::aPartirDe);
    }
}
