package com.sistran.fastclaims.application.dominio.atualizar;

public record AtualizarDominioCommand(String id, String nome, String descricao) {

    public static AtualizarDominioCommand com(final String id, final String nome, final String descricao) {
        return new AtualizarDominioCommand(id, nome, descricao);
    }
}
