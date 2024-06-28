package com.sistran.fastclaims.application.regra.pesquisar.lista;

import com.sistran.fastclaims.domain.regra.Regra;

import java.time.Instant;

public record ListarRegrasOutput(
        String id,
        String nome,
        String descricao,
        String campoUm,
        String operadorUm,
        String campoDois,
        String operadorDois,
        String campoTres,
        Instant dataCadastro
) {

    public static ListarRegrasOutput aPartirDe(final Regra regra) {
        return new ListarRegrasOutput(
                regra.getId().getValue(),
                regra.getNome(),
                regra.getDescricao(),
                regra.getCampoUm().getValue(),
                regra.getOperadorUm().getValue(),
                regra.getCampoDois(),
                regra.getOperadorDois().getValue(),
                regra.getCampoTres(),
                regra.getDataCadastro()
        );
    }
}


