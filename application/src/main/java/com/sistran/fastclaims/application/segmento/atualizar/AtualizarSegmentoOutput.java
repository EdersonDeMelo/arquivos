package com.sistran.fastclaims.application.segmento.atualizar;

import com.sistran.fastclaims.domain.segmento.Segmento;

public record AtualizarSegmentoOutput(
        String id,
        String nome
) {

    public static AtualizarSegmentoOutput aPartirDe(final Segmento segmento) {
        return new AtualizarSegmentoOutput(
                segmento.getId().getValue(),
                segmento.getNome()
        );
    }
}

