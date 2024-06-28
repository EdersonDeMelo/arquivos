package com.sistran.fastclaims.infrastructure.configuration.usecases;


import com.sistran.fastclaims.application.fluxo.atualizar.AtualizarFluxoUseCase;
import com.sistran.fastclaims.application.fluxo.atualizar.DefaultAtualizarFluxoUseCase;
import com.sistran.fastclaims.application.fluxo.cadastrar.CadastrarFluxoUseCase;
import com.sistran.fastclaims.application.fluxo.cadastrar.DefaultCadastrarFluxoUseCase;
import com.sistran.fastclaims.application.fluxo.excluir.DefaultExcluirFluxoUseCase;
import com.sistran.fastclaims.application.fluxo.excluir.ExcluirFluxoUseCase;
import com.sistran.fastclaims.application.fluxo.pesquisar.id.DefaultPesquisarFluxoPorIdUseCase;
import com.sistran.fastclaims.application.fluxo.pesquisar.id.PesquisarFluxoPorIdUseCase;
import com.sistran.fastclaims.application.fluxo.pesquisar.lista.DefaultListarFluxosUseCase;
import com.sistran.fastclaims.application.fluxo.pesquisar.lista.ListarFluxosUseCase;
import com.sistran.fastclaims.domain.fluxo.FluxoGateway;
import com.sistran.fastclaims.domain.natureza.NaturezaGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class FluxoUseCaseConfig {

    @Bean
    public CadastrarFluxoUseCase cadastrarFluxoUseCase(final FluxoGateway fluxoGateway, final NaturezaGateway naturezaGateway) {
        return new DefaultCadastrarFluxoUseCase(fluxoGateway, naturezaGateway);
    }


    @Bean
    public PesquisarFluxoPorIdUseCase pesquisarFluxoPorIdUseCase(final FluxoGateway fluxoGateway) {
        return new DefaultPesquisarFluxoPorIdUseCase(fluxoGateway);
    }

    @Bean
    public ListarFluxosUseCase pesquisarFluxoPorTermoUseCase(final FluxoGateway fluxoGateway) {
        return new DefaultListarFluxosUseCase(fluxoGateway);
    }

    @Bean
    public AtualizarFluxoUseCase alterarFluxoPorIdUseCase(final FluxoGateway fluxoGateway) {
        return new DefaultAtualizarFluxoUseCase(fluxoGateway);
    }

    @Bean
    public ExcluirFluxoUseCase excluirFluxoUseCase(final FluxoGateway fluxoGateway) {
        return new DefaultExcluirFluxoUseCase(fluxoGateway);
    }
}
