package com.sistran.fastclaims.application.regra.pesquisar.lista;

import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;

public abstract class ListarRegrasUseCase extends UseCase<SearchQuery, Pagination<ListarRegrasOutput>> {

}
