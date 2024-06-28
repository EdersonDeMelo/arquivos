package com.sistran.fastclaims.domain.colecaocampo;

import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;

import java.util.Optional;

public interface ColecaoCampoGateway {

    ColecaoCampo cadastrar(ColecaoCampo colecaoCampo);

    Optional<ColecaoCampo> pesquisarPorColecaoId(ColecaoID colecaoID);

    Optional<ColecaoCampo> pesquisarPorId(ColecaoCampoID colecaoCampoID);

    Pagination<ColecaoCampo> listar(SearchQuery query);

    void excluir(ColecaoCampoID id);

}
