package com.sistran.fastclaims.application.segmento.pesquisar.id;

import com.sistran.fastclaims.domain.segmento.Segmento;

public record PesquisarSegmentoPorIdOutput(
        String id,
        String nome
) {

    public static PesquisarSegmentoPorIdOutput aPartirDe(Segmento segmento) {
        return new PesquisarSegmentoPorIdOutput(segmento.getId().toString(), segmento.getNome());
    }
}
