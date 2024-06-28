package com.sistran.fastclaims.application.fluxo.atualizar;

public record AtualizarFluxoCommand(String id, String descricao) {

    public static AtualizarFluxoCommand com(final String id, final String descricao) {
        return new AtualizarFluxoCommand(id, descricao);
    }
}






