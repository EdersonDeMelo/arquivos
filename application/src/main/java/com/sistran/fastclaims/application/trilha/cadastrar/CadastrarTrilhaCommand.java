package com.sistran.fastclaims.application.trilha.cadastrar;

public record CadastrarTrilhaCommand(
        String nome,
        String descricao,
        String fluxoId
) {

    public static CadastrarTrilhaCommand com(
            final String nome,
            final String descricao,
            final String fluxoId
    ) {
        return new CadastrarTrilhaCommand(nome, descricao, fluxoId);
    }
}
