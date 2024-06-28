package com.sistran.fastclaims.application.regra.cadastrar;

import com.sistran.fastclaims.domain.regra.Regra;
import com.sistran.fastclaims.domain.regra.RegraID;

import java.time.Instant;

public record CadastrarRegraOutput(
        RegraID id,
        String nome,
        String descricao,
        String campoUm,
        String operadorUm,
        String campoDois,
        String operadorDois,
        String campoTres,
        Instant dataCadastro
) {

    public static CadastrarRegraOutput aPartirDe(final Regra regra) {
        return new CadastrarRegraOutput(
                regra.getId(),
                regra.getNome(),
                regra.getDescricao(),
                regra.getCampoUm().getValue(),
                regra.getOperadorUm().getValue(),
                regra.getCampoDois(),
                regra.getOperadorDois().getValue(),
                regra.getCampoTres(),
                regra.getDataCadastro());
    }

    ;
}
