package com.sistran.fastclaims.application.grupousuario.pesquisar.nome;

import com.sistran.fastclaims.domain.grupousuario.GrupoUsuario;
import com.sistran.fastclaims.domain.grupousuario.GrupoUsuarioID;

import java.time.Instant;

public record ListarGrupoUsuarioPorNomeOutput(
        GrupoUsuarioID id,
        String nome,
        Instant dataCadastro,
        Instant dataAtualizacao,
        Boolean ativo
) {

    public static ListarGrupoUsuarioPorNomeOutput aPartirDe(final GrupoUsuario grupoUsuario) {
        return new ListarGrupoUsuarioPorNomeOutput(
                grupoUsuario.getId(),
                grupoUsuario.getNome(),
                grupoUsuario.getDataCadastro(),
                grupoUsuario.getDataAlteracao(),
                grupoUsuario.getAtivo()
        );
    }
}
