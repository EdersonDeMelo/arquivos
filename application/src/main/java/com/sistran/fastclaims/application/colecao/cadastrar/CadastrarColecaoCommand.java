package com.sistran.fastclaims.application.colecao.cadastrar;

public record CadastrarColecaoCommand(String nome, String alias) {

    public static CadastrarColecaoCommand com(final String nome, final String alias) {
        return new CadastrarColecaoCommand(nome, alias);
    }
}
