package com.sistran.fastclaims.application.segmento.pesquisar.lista;

import com.sistran.fastclaims.domain.segmento.Segmento;

public record ListarSegmentosOutput(
        String id,
        String nome
) {

    public static ListarSegmentosOutput aPartirDe(final Segmento segmento) {
        return new ListarSegmentosOutput(segmento.getId().getValue(), segmento.getNome());
    }
}


