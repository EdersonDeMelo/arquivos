package com.sistran.fastclaims.application.grupousuario.cadastrar;

import com.sistran.fastclaims.domain.grupousuario.GrupoUsuario;
import com.sistran.fastclaims.domain.grupousuario.GrupoUsuarioID;

import java.time.Instant;

public record CadastrarGrupoUsuarioOutput(GrupoUsuarioID id, String nome, Instant dataCadastro,
                                          Instant dataAlteracao, Boolean ativo) {

    public static CadastrarGrupoUsuarioOutput aPartirDe(final GrupoUsuario grupoUsuario) {
        return new CadastrarGrupoUsuarioOutput(grupoUsuario.getId(), grupoUsuario.getNome(), grupoUsuario.getDataCadastro(),
                grupoUsuario.getDataAlteracao(), grupoUsuario.getAtivo());
    }
}
