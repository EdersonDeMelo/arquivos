package com.sistran.fastclaims.infrastructure.grupousuario.models;

import java.time.Instant;

public record GrupoUsuarioResponse(
        String id,
        String nome,
        Instant dataCadastro,
        Instant dataAlteracao,
        boolean ativo
) {

    public static GrupoUsuarioResponse aPartirDe(final String id, final String nome, final Instant dataCadastro, final Instant dataAlteracao, final boolean ativo) {
        return new GrupoUsuarioResponse(id, nome, dataCadastro, dataAlteracao, ativo);
    }
}
