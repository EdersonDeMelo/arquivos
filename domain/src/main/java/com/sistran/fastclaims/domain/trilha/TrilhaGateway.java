package com.sistran.fastclaims.domain.trilha;

import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;
import com.sistran.fastclaims.domain.regra.Regra;

import java.util.Optional;

public interface TrilhaGateway {

    Optional<Trilha> pesquisarPorId(TrilhaID id);

    Pagination<Trilha> listar(SearchQuery query);

    Trilha cadastrar(Trilha trilha);

    Trilha atualizar(Trilha trilha);

    void ativar(Trilha trilha);

    void desativar(Trilha trilha);

    void excluir(TrilhaID id);

    RegraTrilhaPreview cadastrarRegraTrilha(Trilha trilha, Regra regra, RegraTrilha regraTrilha);

    RegraTrilha atualizarRegraTrilha(Trilha trilha, RegraTrilha regraTrilha);

    void ativarRegraTrilha(Trilha trilha);

    void desativarRegraTrilha(Trilha trilha);

}



