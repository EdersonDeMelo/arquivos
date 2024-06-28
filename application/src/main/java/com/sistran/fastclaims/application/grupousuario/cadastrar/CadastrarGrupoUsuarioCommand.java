package com.sistran.fastclaims.application.grupousuario.cadastrar;

public record CadastrarGrupoUsuarioCommand(String nome) {

    public static CadastrarGrupoUsuarioCommand com(final String nome) {
        return new CadastrarGrupoUsuarioCommand(nome);
    }
}
