package com.sistran.fastclaims.application.trilha.atualizar;


public record AtualizarTrilhaCommand(
        String id,
        String nome,
        String descricao
) {
    public static AtualizarTrilhaCommand com(
            String id,
            String nome,
            String descricao

    ) {
        return new AtualizarTrilhaCommand(id, nome, descricao);
    }
}
