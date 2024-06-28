package com.sistran.fastclaims.domain.grupousuario;

import java.util.Optional;

public interface GrupoUsuarioGateway {

    GrupoUsuario cadastrar(GrupoUsuario grupoUsuario);

   Optional<GrupoUsuario> pesquisarPorId(GrupoUsuarioID id);

   Optional<GrupoUsuario> pesquisarPorNome(String nome);
}
