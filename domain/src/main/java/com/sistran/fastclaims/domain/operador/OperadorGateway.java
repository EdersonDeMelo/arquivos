package com.sistran.fastclaims.domain.operador;

import java.util.List;
import java.util.Optional;

public interface OperadorGateway {

    Optional<Operador> pesquisarPorId(OperadorID id);

    List<Operador> listar();

}
