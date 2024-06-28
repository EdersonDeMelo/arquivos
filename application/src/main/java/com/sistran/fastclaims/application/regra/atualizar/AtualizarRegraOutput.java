package com.sistran.fastclaims.application.regra.atualizar;

import com.sistran.fastclaims.domain.regra.Regra;

import java.time.Instant;

public record AtualizarRegraOutput(
        String id,
        String nome,
        String descricao,
        String campoUm,
        String operadorUm,
        String campoDois,
        String operadorDois,
        String campoTres,
        Instant dataAlteracao
) {

    public static AtualizarRegraOutput aPartirDe(final Regra regra) {
        return new AtualizarRegraOutput(
                regra.getId().getValue(),
                regra.getNome(),
                regra.getDescricao(),
                regra.getCampoUm().getValue(),
                regra.getOperadorUm().getValue(),
                regra.getCampoDois(),
                regra.getOperadorDois().getValue(),
                regra.getCampoTres(),
                regra.getDataAlteracao());
    }
}
