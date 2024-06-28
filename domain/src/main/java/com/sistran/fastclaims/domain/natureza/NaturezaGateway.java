package com.sistran.fastclaims.domain.natureza;

import java.util.List;
import java.util.Optional;

public interface NaturezaGateway {

    Optional<Natureza> pesquisarPorId(NaturezaID naturezaID);

    List<Natureza> listar(String termo);
}
