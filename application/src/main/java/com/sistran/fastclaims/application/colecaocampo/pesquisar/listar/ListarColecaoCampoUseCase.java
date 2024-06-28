package com.sistran.fastclaims.application.colecaocampo.pesquisar.listar;

import com.sistran.fastclaims.application.UseCase;
import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;

public abstract class ListarColecaoCampoUseCase extends UseCase<SearchQuery, Pagination<ListarColecaoCampoOutput>> {
}
