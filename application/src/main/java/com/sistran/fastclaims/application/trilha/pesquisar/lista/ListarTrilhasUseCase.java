package com.sistran.fastclaims.application.trilha.pesquisar.lista;

import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;

public abstract class ListarTrilhasUseCase extends UseCase<SearchQuery, Pagination<ListarTrilhasOutput>> {
}
