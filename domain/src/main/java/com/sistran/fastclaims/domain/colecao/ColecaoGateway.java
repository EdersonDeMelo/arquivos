package com.sistran.fastclaims.domain.colecao;

import java.util.Optional;

public interface ColecaoGateway {

    Colecao cadastrar(final Colecao colecao);

    Optional<Colecao> pesquisarPorId(final ColecaoID id);

    void excluirPorId(final ColecaoID id);

    Colecao atualizar(final Colecao colecao);

}
