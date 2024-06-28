package com.sistran.fastclaims.application.segmento.atualizar;

public record AtualizarSegmentoCommand(
        String id,
        String nome
) {

    public static AtualizarSegmentoCommand aPartirDe(String id, String nome) {
        return new AtualizarSegmentoCommand(id, nome);
    }
}
