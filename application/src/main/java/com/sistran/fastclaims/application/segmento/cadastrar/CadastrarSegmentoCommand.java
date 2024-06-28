package com.sistran.fastclaims.application.segmento.cadastrar;

public record CadastrarSegmentoCommand(
        String nome
) {

    public static CadastrarSegmentoCommand com(String nome) {
        return new CadastrarSegmentoCommand(nome);
    }
}
