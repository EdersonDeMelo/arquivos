package com.sistran.fastclaims.infrastructure.dominio.models;

import com.sistran.fastclaims.infrastructure.dominiovalor.models.DominioValorResponse;

import java.time.Instant;
import java.util.List;

public record DominioResponse(String id, String nome, String descricao, Instant dataCadastro,
                              List<DominioValorResponse> valores) {

    public static DominioResponse aPartirDe(final String id, final String nome, final String descricao, final Instant dataCadastro, final List<DominioValorResponse> valores) {
        return new DominioResponse(id, nome, descricao, dataCadastro, valores);
    }
}
