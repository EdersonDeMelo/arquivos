package com.sistran.fastclaims.application.dominio.atualizar;

import com.sistran.fastclaims.domain.dominio.Dominio;
import com.sistran.fastclaims.domain.dominio.DominioID;

import java.time.Instant;

public record AtualizarDominioOutput(
        DominioID id,
        String nome,
        String descricao,
        Instant dataCadastro,
        Instant dataAlteracao
) {

    public static AtualizarDominioOutput aPartirDe(final Dominio dominio) {
        return new AtualizarDominioOutput(
                dominio.getId(),
                dominio.getNome(),
                dominio.getDescricao(),
                dominio.getDataCadastro(),
                dominio.getDataAlteracao()
        );
    }
}
