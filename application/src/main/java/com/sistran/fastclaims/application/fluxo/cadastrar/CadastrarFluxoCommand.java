package com.sistran.fastclaims.application.fluxo.cadastrar;

public record CadastrarFluxoCommand(String descricao, String naturezaId) {

    public static CadastrarFluxoCommand com(final String descricao, final String naturezaId) {
        return new CadastrarFluxoCommand(descricao, naturezaId);
    }
}
