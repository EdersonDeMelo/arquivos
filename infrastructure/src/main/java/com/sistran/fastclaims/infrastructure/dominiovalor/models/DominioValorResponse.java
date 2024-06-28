package com.sistran.fastclaims.infrastructure.dominiovalor.models;


public record DominioValorResponse(
        String id,
        String codigoValor,
        String nomeValor
) {

    public static DominioValorResponse aPartirDe(String id, String codigoValor, String nomeValor) {
        return new DominioValorResponse(id, codigoValor, nomeValor);
    }
}
