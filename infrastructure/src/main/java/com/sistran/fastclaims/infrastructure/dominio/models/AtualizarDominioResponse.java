package com.sistran.fastclaims.infrastructure.dominio.models;

import java.time.Instant;

public record AtualizarDominioResponse (String id, String nome, String descricao, Instant dataCadastro, Instant dataAlteracao){

    public static AtualizarDominioResponse aPartirDe(final String id, final String nome, final String descricao, final Instant dataCadastro, final Instant dataAlteracao) {
        return new AtualizarDominioResponse(id, nome, descricao, dataCadastro, dataAlteracao);
    }


}
