package com.sistran.fastclaims.domain.regra;

import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoID;
import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;

import java.util.Optional;

public interface RegraGateway {

    Regra cadastrar(Regra regra);

    Optional<Regra> pesquisarPorId(RegraID id);

    Regra atualizar(Regra regra);

    void excluir(RegraID id);

    Pagination<Regra> listar(SearchQuery query);

    Optional<Regra> pesquisarCampoUm(ColecaoCampoID id);
    Optional<Regra> pesquisarCampoDois(ColecaoCampoID id);
    Optional<Regra> pesquisarCampoTres(ColecaoCampoID id);
}
