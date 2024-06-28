package com.sistran.fastclaims.domain.fluxo;

import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;

import java.util.Optional;

public interface FluxoGateway {

    Fluxo cadastrar(Fluxo fluxo);

    Optional<Fluxo> pesquisarPorId(FluxoID id);

    Pagination<Fluxo> listar(SearchQuery query);

    Fluxo atualizar(Fluxo fluxo);

    void excluirPorId(FluxoID id);
}

