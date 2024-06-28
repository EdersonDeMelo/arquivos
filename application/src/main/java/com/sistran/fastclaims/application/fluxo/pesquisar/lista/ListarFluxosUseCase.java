package com.sistran.fastclaims.application.fluxo.pesquisar.lista;


import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;

public abstract class ListarFluxosUseCase extends UseCase<SearchQuery, Pagination<ListarFluxosOutput>> {
}
