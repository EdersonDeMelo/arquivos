package com.sistran.fastclaims.infrastructure.segmento.models;

public record SegmentoResponse(
        String id,
        String nome
) {

    public static SegmentoResponse aPartirDe(String id, String nome) {
        return new SegmentoResponse(id, nome);
    }
}
