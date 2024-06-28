package com.sistran.fastclaims.infrastructure.configuration.usecases;

import com.sistran.fastclaims.application.grupousuario.cadastrar.CadastrarGrupoUsuarioUseCase;
import com.sistran.fastclaims.application.grupousuario.cadastrar.DefaultCadastrarGrupoUsuarioUseCase;
import com.sistran.fastclaims.application.grupousuario.pesquisar.id.DefaultPesquisarGrupoUsuarioPorIdUseCase;
import com.sistran.fastclaims.application.grupousuario.pesquisar.id.PesquisarGrupoUsuarioPorIdUseCase;
import com.sistran.fastclaims.application.grupousuario.pesquisar.nome.DefaultPesquisarGrupoUsuarioPorNomeUseCase;
import com.sistran.fastclaims.application.grupousuario.pesquisar.nome.PesquisarGrupoUsuarioPorNomeUseCase;
import com.sistran.fastclaims.domain.grupousuario.GrupoUsuarioGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration (proxyBeanMethods = false)
public class GrupoUsuarioUseCaseConfig {

    @Bean
    public CadastrarGrupoUsuarioUseCase cadastrarGrupoUsuarioUseCase(final GrupoUsuarioGateway grupoUsuarioGateway) {
        return new DefaultCadastrarGrupoUsuarioUseCase(grupoUsuarioGateway);
    }

    @Bean
    public PesquisarGrupoUsuarioPorIdUseCase pesquisarGrupoUsuarioPorIdUseCase(final GrupoUsuarioGateway grupoUsuarioGateway) {
        return new DefaultPesquisarGrupoUsuarioPorIdUseCase(grupoUsuarioGateway);
    }

    @Bean
    public PesquisarGrupoUsuarioPorNomeUseCase pesquisarGrupoUsuarioPorNomeUseCase(final GrupoUsuarioGateway grupoUsuarioGateway) {
        return new DefaultPesquisarGrupoUsuarioPorNomeUseCase(grupoUsuarioGateway);
    }
}
