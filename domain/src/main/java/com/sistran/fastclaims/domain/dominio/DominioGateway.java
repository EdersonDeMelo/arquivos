package com.sistran.fastclaims.domain.dominio;

import java.util.List;
import java.util.Optional;

public interface DominioGateway {

    Dominio cadastrar(Dominio dominio);

    Optional<Dominio> pesquisarPorNome(String nome);

    Optional<Dominio> pesquisarPorId(DominioID id);

    List<Dominio> listarDominios();

    Dominio atualizar(Dominio dominio);

    Dominio adicionarValor(Dominio dominio);

    boolean exists(DominioID id);

}
