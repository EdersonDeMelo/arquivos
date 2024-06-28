package com.sistran.fastclaims.application.colecao.atualizar;

public record AtualizarColecaoCommand(String id, String alias) {

    public static AtualizarColecaoCommand com(final String id, final String alias) {
        return new AtualizarColecaoCommand(id, alias);
    }
}
