package com.sistran.fastclaims.application.dominiovalor.excluir;

public record ExcluirDominioValorCommand(
        String dominioId,
        String dominioValorId

) {

    public static ExcluirDominioValorCommand com(final String dominioId, final String dominioValorId) {
        return new ExcluirDominioValorCommand(dominioId, dominioValorId);
    }
}
