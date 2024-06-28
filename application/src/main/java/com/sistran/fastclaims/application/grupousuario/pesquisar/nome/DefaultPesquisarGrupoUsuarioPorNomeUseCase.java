package com.sistran.fastclaims.application.grupousuario.pesquisar.nome;

import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.ParametroInexistenteException;
import com.sistran.fastclaims.domain.grupousuario.GrupoUsuario;
import com.sistran.fastclaims.domain.grupousuario.GrupoUsuarioGateway;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.function.Supplier;

public class DefaultPesquisarGrupoUsuarioPorNomeUseCase extends PesquisarGrupoUsuarioPorNomeUseCase{

    private final GrupoUsuarioGateway grupoUsuarioGateway;

    public DefaultPesquisarGrupoUsuarioPorNomeUseCase(GrupoUsuarioGateway grupoUsuarioGateway) {
        this.grupoUsuarioGateway = grupoUsuarioGateway;
    }

    @Override
    public Either<Notification, ListarGrupoUsuarioPorNomeOutput> executar(String nomeGrupoUsuario) {
        return grupoUsuarioGateway.pesquisarPorNome(nomeGrupoUsuario)
                .map(ListarGrupoUsuarioPorNomeOutput::aPartirDe)
                .map(Either::<Notification, ListarGrupoUsuarioPorNomeOutput>right)
                .orElseThrow(grupoUsuarioNaoEncontrado(nomeGrupoUsuario));
    }

    private Supplier<DomainException> grupoUsuarioNaoEncontrado(final String nomeGrupoUsuario) {
        return () -> ParametroInexistenteException.with(GrupoUsuario.class, nomeGrupoUsuario);
    }
}
