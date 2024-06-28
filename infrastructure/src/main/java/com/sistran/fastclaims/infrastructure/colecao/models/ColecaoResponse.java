package com.sistran.fastclaims.infrastructure.colecao.models;

public record ColecaoResponse(String id, String nome, String alias) {

    public static ColecaoResponse aPartirDe(final String id, final String nome, final String alias) {
        return new ColecaoResponse(id, nome, alias);
    }

}
