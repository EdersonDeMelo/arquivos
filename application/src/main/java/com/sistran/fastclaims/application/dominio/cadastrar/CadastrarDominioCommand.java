package com.sistran.fastclaims.application.dominio.cadastrar;

import com.sistran.fastclaims.domain.dominiovalor.DominioValor;

import java.util.List;

public record CadastrarDominioCommand(String nome, String descricao, List<DominioValor> valores) {

    public static CadastrarDominioCommand com(final String nome, final String descricao, List<DominioValor> valores) {
        return new CadastrarDominioCommand(nome, descricao, valores);
    }
}
