package com.sistran.fastclaims.application.dominiovalor.cadastrar;

import com.sistran.fastclaims.domain.dominio.DominioID;

public record CadastrarDominioValorCommand(DominioID dominioid, String codigoValor, String nomeValor) {

    public static CadastrarDominioValorCommand com(
             final DominioID dominioId, final String codigoValor, final String nomeValor) {
        return new CadastrarDominioValorCommand(dominioId, codigoValor, nomeValor);
    }
}
