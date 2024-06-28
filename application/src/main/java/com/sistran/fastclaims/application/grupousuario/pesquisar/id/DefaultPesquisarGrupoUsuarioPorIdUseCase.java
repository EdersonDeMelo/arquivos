package com.sistran.fastclaims.application.grupousuario.pesquisar.id;

import com.sistran.fastclaims.domain.dominio.Dominio;
import com.sistran.fastclaims.domain.dominio.DominioID;
import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.ParametroInexistenteException;
import com.sistran.fastclaims.domain.grupousuario.GrupoUsuario;
import com.sistran.fastclaims.domain.grupousuario.GrupoUsuarioGateway;
import com.sistran.fastclaims.domain.grupousuario.GrupoUsuarioID;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.function.Supplier;

public class DefaultPesquisarGrupoUsuarioPorIdUseCase extends PesquisarGrupoUsuarioPorIdUseCase{

    private GrupoUsuarioGateway grupoUsuarioGateway;

    public DefaultPesquisarGrupoUsuarioPorIdUseCase(GrupoUsuarioGateway grupoUsuarioGateway) {
        this.grupoUsuarioGateway = grupoUsuarioGateway;
    }

    @Override
    public Either<Notification, PesquisarGrupoUsuarioPorIdOutput> executar(String id) {
        final var grupoUsuarioId = GrupoUsuarioID.from(id);

        return grupoUsuarioGateway.pesquisarPorId(grupoUsuarioId)
                .map(PesquisarGrupoUsuarioPorIdOutput::aPartirDe)
                .map(Either::<Notification, PesquisarGrupoUsuarioPorIdOutput>right)
                .orElseThrow(grupoUsuarioNaoEncontrado(grupoUsuarioId));

    }
    private static Supplier<DomainException> grupoUsuarioNaoEncontrado(final GrupoUsuarioID id) {
        return () -> ParametroInexistenteException.with(GrupoUsuario.class, id);
    }
}
