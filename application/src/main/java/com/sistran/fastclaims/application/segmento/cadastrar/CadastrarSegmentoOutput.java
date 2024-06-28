package com.sistran.fastclaims.application.segmento.cadastrar;

import com.sistran.fastclaims.domain.segmento.Segmento;

public record CadastrarSegmentoOutput(
        String id,
        String nome,
        String dataCadastro
) {

    public static CadastrarSegmentoOutput aPartirDe(Segmento segmento) {
        return new CadastrarSegmentoOutput(segmento.getId().getValue(), segmento.getNome(), segmento.getDataCadastro().toString());
    }
}
