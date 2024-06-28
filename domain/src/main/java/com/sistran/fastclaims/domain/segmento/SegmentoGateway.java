package com.sistran.fastclaims.domain.segmento;

import java.util.List;
import java.util.Optional;

public interface SegmentoGateway {

    Optional<Segmento> pesquisarPorId(SegmentoID id);

    List<Segmento> listar();

    Segmento cadastrar(Segmento segmento);

    Segmento atualizar(Segmento segmento);


}
