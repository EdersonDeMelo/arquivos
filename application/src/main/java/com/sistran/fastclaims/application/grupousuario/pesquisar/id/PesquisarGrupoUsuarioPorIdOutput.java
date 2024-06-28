package com.sistran.fastclaims.application.grupousuario.pesquisar.id;

import com.sistran.fastclaims.domain.grupousuario.GrupoUsuario;
import com.sistran.fastclaims.domain.grupousuario.GrupoUsuarioID;

import java.time.Instant;

public record PesquisarGrupoUsuarioPorIdOutput(GrupoUsuarioID id, String nome, Instant dataCadastro, Instant dataAtualizacao, Boolean ativo) {

    public static PesquisarGrupoUsuarioPorIdOutput aPartirDe(final GrupoUsuario grupoUsuario) {
        return new PesquisarGrupoUsuarioPorIdOutput(grupoUsuario.getId(), grupoUsuario.getNome(), grupoUsuario.getDataCadastro(),
                grupoUsuario.getDataAlteracao(), grupoUsuario.getAtivo());
    }
}
